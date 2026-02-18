import json
from pathlib import Path

import numpy as np

# ---------------------------------------------------------------------------
# Constants and paths
# ---------------------------------------------------------------------------

INFTY = 9999  # Sentinel for ineligible (operation, machine) pairs

# Base directory = folder where this file (instance.py) is located
BASE_DIR = Path(__file__).resolve().parent
DEFAULT_DATA_DIR = BASE_DIR / "Data"
INSTANCES_JSON = DEFAULT_DATA_DIR / "instances.json"


# ---------------------------------------------------------------------------
# 1) Simple synthetic generator (your original one, kept as-is)
# ---------------------------------------------------------------------------

def generate_fjssp_instance(difficulty, SEED):
    """
    Generate a synthetic Flexible Job Shop Scheduling Problem (FJSSP) instance.

    This is the original simple generator: all jobs have the same number of
    operations, and routing flexibility is controlled by a single
    ineligible_ratio parameter.

    Parameters
    ----------
    difficulty : {"easy", "medium", "hard"}
    SEED : int

    Returns
    -------
    Processing_time : list
        3-D list: Processing_time[j][o][m] = processing time or 9999.
    J : dict
        {job_id (1-based): num_operations_for_this_job}
    M_num : int
        Number of machines.
    O_num : int
        Total number of operations across all jobs.
    J_num : int
        Number of jobs.
    """
    rng = np.random.default_rng(SEED)

    if difficulty == "easy":
        J_num = 5
        O_per_job = 3
        M_num = 4
        proc_time_range = (5, 15)
        ineligible_ratio = 0.2
    elif difficulty == "medium":
        J_num = 8
        O_per_job = 5
        M_num = 6
        proc_time_range = (5, 25)
        ineligible_ratio = 0.5
    elif difficulty == "hard":
        J_num = 10
        O_per_job = 8
        M_num = 10
        proc_time_range = (5, 50)
        ineligible_ratio = 0.8
    else:
        raise ValueError("Unknown difficulty: {}".format(difficulty))

    Processing_time = []

    for _job in range(J_num):
        job_ops = []
        for _op in range(O_per_job):
            op_proc = []
            for _m in range(M_num):
                # Randomly make some machines ineligible
                if rng.random() < ineligible_ratio:
                    op_proc.append(INFTY)
                else:
                    op_proc.append(
                        int(rng.integers(proc_time_range[0],
                                         proc_time_range[1] + 1))
                    )

            # Ensure at least one machine is eligible
            if all(pt == INFTY for pt in op_proc):
                m_fix = int(rng.integers(0, M_num))
                op_proc[m_fix] = int(
                    rng.integers(proc_time_range[0],
                                 proc_time_range[1] + 1)
                )

            job_ops.append(op_proc)
        Processing_time.append(job_ops)

    J = {i + 1: O_per_job for i in range(J_num)}
    O_num = J_num * O_per_job

    return Processing_time, J, M_num, O_num, J_num


# ---------------------------------------------------------------------------
# 2) Benchmark-like synthetic generator (heterogeneous jobs & routing)
# ---------------------------------------------------------------------------

def generate_fjssp_like_benchmark(
    J_num_range=(8, 30),
    M_num_range=(5, 15),
    O_per_job_range=(3, 10),
    machines_per_op_range=(1, 4),
    proc_time_range=(1, 99),
    SEED=0,
):
    """
    Generate synthetic FJSSP instances with a structure resembling the
    classical benchmark sets (Brandimarte, Hurink, Barnes, etc.).

    - Different number of operations per job
    - Each operation has a random subset of machines that can process it
    - Processing times are positive integers

    Returns
    -------
    Processing_time, J, M_num, O_num, J_num
      Same format as generate_fjssp_instance.
    """
    rng = np.random.default_rng(SEED)

    J_num = int(rng.integers(J_num_range[0], J_num_range[1] + 1))
    M_num = int(rng.integers(M_num_range[0], M_num_range[1] + 1))

    Processing_time = []
    J = {}

    for job_id in range(1, J_num + 1):
        num_ops = int(
            rng.integers(O_per_job_range[0], O_per_job_range[1] + 1)
        )
        J[job_id] = num_ops

        job_ops = []
        for _op in range(num_ops):
            max_k = min(machines_per_op_range[1], M_num)
            k = int(
                rng.integers(machines_per_op_range[0], max_k + 1)
            )

            machines = rng.choice(M_num, size=k, replace=False)
            op_proc = [INFTY] * M_num

            for m in machines:
                op_proc[m] = int(
                    rng.integers(proc_time_range[0],
                                 proc_time_range[1] + 1)
                )

            job_ops.append(op_proc)
        Processing_time.append(job_ops)

    O_num = sum(J.values())
    return Processing_time, J, M_num, O_num, J_num


# ---------------------------------------------------------------------------
# 3) Helpers for instances.json
# ---------------------------------------------------------------------------

def _load_instances_metadata(data_dir=DEFAULT_DATA_DIR):
    """
    Load metadata for all benchmark instances from instances.json.

    Returns
    -------
    list of dict
        Each dict typically has keys:
        - name
        - jobs
        - machines
        - optimum
        - path (relative path to the .txt file)
    """
    json_path = Path(data_dir) / "instances.json"
    with json_path.open("r", encoding="utf-8") as f:
        instances = json.load(f)
    return instances


def list_available_instances(data_dir=DEFAULT_DATA_DIR):
    """
    Return the list of all available instance names in instances.json.
    """
    instances = _load_instances_metadata(data_dir)
    return [inst["name"] for inst in instances]


def get_instance_metadata(name, data_dir=DEFAULT_DATA_DIR):
    """
    Get the metadata dict for a given instance name.

    Parameters
    ----------
    name : str
        Instance name as in instances.json, e.g. "mk02", "mt10c1", etc.

    Returns
    -------
    dict

    Raises
    ------
    KeyError if not found.
    """
    instances = _load_instances_metadata(data_dir)
    name_lower = name.lower()
    for inst in instances:
        if inst["name"].lower() == name_lower:
            return inst
    raise KeyError(
        "Instance '{}' not found in {}".format(
            name, Path(data_dir) / "instances.json"
        )
    )


# ---------------------------------------------------------------------------
# 4) Parser for *.txt benchmark files (SchedulingLab / fjsp-instances)
# ---------------------------------------------------------------------------

def _parse_fjsp_file(path):
    """
    Parse a SchedulingLab FJSP benchmark file (.txt) with format:

    Line 1:
        <number_of_jobs> <number_of_machines>

    Then, for each job j = 1..J:
        One line containing:
            <num_ops>
            [for each operation o = 1..num_ops:
                <k> (number of feasible machines)
                (m1 p1) (m2 p2) ... (mk pk)
            ]

    Machine indices in the files are 0-based.

    Parameters
    ----------
    path : Path

    Returns
    -------
    Processing_time, J, M_num, O_num, J_num
    """
    path = Path(path)
    with path.open("r", encoding="utf-8") as f:
        first_line = f.readline().split()
        if len(first_line) < 2:
            raise ValueError("Invalid header in {}".format(path))

        J_num = int(first_line[0])
        M_num = int(first_line[1])

        Processing_time = []
        J = {}

        for job_idx in range(J_num):
            line = f.readline()
            if not line:
                raise ValueError(
                    "Unexpected end-of-file in {} at job {}".format(
                        path, job_idx
                    )
                )
            parts = [int(x) for x in line.split()]
            pos = 0

            # Number of operations for this job
            num_ops = parts[pos]
            pos += 1
            J[job_idx + 1] = num_ops

            job_ops = []
            for _op in range(num_ops):
                if pos >= len(parts):
                    raise ValueError(
                        "Line for job {} too short in {}".format(
                            job_idx, path
                        )
                    )

                k_machines = parts[pos]
                pos += 1

                op_proc = [INFTY] * M_num

                # k_machines pairs: (machine_id, processing_time)
                for _k in range(k_machines):
                    if pos + 1 >= len(parts):
                        raise ValueError(
                            "Not enough (machine,time) pairs "
                            "for job {} in {}".format(job_idx, path)
                        )
                    m_id = parts[pos]
                    p_t = parts[pos + 1]
                    pos += 2

                    if m_id < 0 or m_id >= M_num:
                        raise ValueError(
                            "Machine index {} out of range "
                            "in {}".format(m_id, path)
                        )
                    op_proc[m_id] = p_t

                job_ops.append(op_proc)

            Processing_time.append(job_ops)

    O_num = sum(J.values())
    return Processing_time, J, M_num, O_num, J_num


# ---------------------------------------------------------------------------
# 5) Public: load a named benchmark instance from Data/
# ---------------------------------------------------------------------------

def load_benchmark_instance(name, data_dir=DEFAULT_DATA_DIR):
    """
    Load a benchmark FJSP instance (Barnes, Brandimarte, Hurink, Kacem, etc.)
    using instances.json and the corresponding .txt file.

    Parameters
    ----------
    name : str
        Instance name as in instances.json, e.g. "mk02", "mt10c1".
    data_dir : Path or str
        Base "Data" directory that contains instances.json and the folders.

    Returns
    -------
    Processing_time, J, M_num, O_num, J_num, meta
        meta is the metadata dict from instances.json.
    """
    meta = get_instance_metadata(name, data_dir=data_dir)
    rel_path = Path(meta["path"])   # e.g. "brandimarte/mk02.txt"
    full_path = Path(data_dir) / rel_path

    if not full_path.is_file():
        raise FileNotFoundError("Instance file not found: {}".format(full_path))

    Processing_time, J, M_num, O_num, J_num = _parse_fjsp_file(full_path)
    return Processing_time, J, M_num, O_num, J_num, meta


# ---------------------------------------------------------------------------
# 6) Unified convenience function
# ---------------------------------------------------------------------------

def get_instance(
    source="benchmark",
    name=None,
    difficulty="medium",
    SEED=0,
    data_dir=DEFAULT_DATA_DIR,
):
    """
    Unified interface to get an FJSSP instance.

    Parameters
    ----------
    source : {"benchmark", "synthetic", "synthetic_like"}
        - "benchmark"      : load from Data/ using instances.json
        - "synthetic"      : original simple generator
        - "synthetic_like" : benchmark-style random generator
    name : str
        Required when source == "benchmark". Example: "mk02", "mt10c1".
    difficulty : str
        Used only when source == "synthetic".
    SEED : int
        Random seed for synthetic generators.
    data_dir : Path or str
        Base Data directory.

    Returns
    -------
    Processing_time, J, M_num, O_num, J_num, extra
        extra:
          - if source == "benchmark": meta dict from instances.json
          - else: {"source": ..., "difficulty": ..., "seed": ...}
    """
    if source == "benchmark":
        if name is None:
            raise ValueError("You must provide 'name' when source='benchmark'.")
        Processing_time, J, M_num, O_num, J_num, meta = load_benchmark_instance(
            name=name,
            data_dir=data_dir,
        )
        print_instance_description(name)
        return Processing_time, J, M_num, O_num, J_num, meta

    elif source == "synthetic":
        Processing_time, J, M_num, O_num, J_num = generate_fjssp_instance(
            difficulty=difficulty,
            SEED=SEED,
        )
        extra = {"source": "synthetic", "difficulty": difficulty, "seed": SEED}
        return Processing_time, J, M_num, O_num, J_num, extra

    elif source == "synthetic_like":
        Processing_time, J, M_num, O_num, J_num = generate_fjssp_like_benchmark(
            SEED=SEED
        )
        extra = {"source": "synthetic_like", "seed": SEED}
        return Processing_time, J, M_num, O_num, J_num, extra

    else:
        raise ValueError("Unknown source: {}".format(source))


def print_instance_description(name, data_dir=DEFAULT_DATA_DIR):
    """
    Print the full description and metadata of a benchmark instance
    from instances.json.

    Parameters
    ----------
    name : str
        Instance name (e.g., "mk02", "mt10c1").
    data_dir : Path
        Base Data directory that contains instances.json.
    """
    try:
        meta = get_instance_metadata(name, data_dir=data_dir)
    except KeyError:
        print(f"Instance '{name}' not found in instances.json.")
        return

    print("\n================ INSTANCE DESCRIPTION ================\n")
    print(f"Name        : {meta.get('name', 'N/A')}")
    print(f"Path        : {meta.get('path', 'N/A')}")
    print(f"Jobs        : {meta.get('jobs', 'N/A')}")
    print(f"Machines    : {meta.get('machines', 'N/A')}")
    optimum = meta.get("optimum")
    if optimum is None:
        print("Optimum     : None (no known optimal value)")
    else:
        print(f"Optimum     : {optimum}")
    bounds = meta.get("bounds")
    if bounds:
        lower = bounds.get("lower", "N/A")
        upper = bounds.get("upper", "N/A")
        print(f"Bounds      : lower={lower}, upper={upper}")

    print("\n======================================================\n")
