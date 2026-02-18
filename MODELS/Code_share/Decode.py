import numpy as np
from Job import Job
from Machine import Machine_Time_window


class Decode:
    def __init__(self, J, Processing_time, M_num):
        """
        Initialize the Decode object, which converts a chromosome (MS+OS) into an actual schedule
        by simulating machine and job operations.

        Parameters
        ----------
        J : dict
            Dictionary where key = job index (1-based), value = number of operations for that job.
            Example: {1: 5, 2: 5, 3: 8, ...}
        Processing_time : list
            3D list where Processing_time[j][o][m] gives the processing time of
            operation o of job j on machine m (9999 = not feasible).
        M_num : int
            Total number of machines.
        """
        self.Processing_time = Processing_time
        self.M_num = M_num
        self.J = J

        # Initialize machine and job states
        self.Machines = []      # List of Machine_Time_window objects
        self.Scheduled = []     # Track operations that have been scheduled (optional)
        self.fitness = 0        # Objective value (makespan)
        self.Machine_State = np.zeros(M_num, dtype=int)  # Tracks which job is on which machine
        self.Jobs = []          # List of Job objects (one per job)

        # Create machine and job instances
        for j in range(M_num):
            self.Machines.append(Machine_Time_window(j))  # Each machine has its own time windows
        for k, v in J.items():
            self.Jobs.append(Job(k, v))                   # Each job knows how many operations it has

    # -------------------------------------------------------------------------
    # Convert the MS part of chromosome into job-machine and processing-time matrices
    # -------------------------------------------------------------------------
    def Order_Matrix(self, MS):
        """
        Convert the Machine Selection (MS) part of a chromosome into:
            - JM : matrix of assigned machine indices for each operation
            - T  : corresponding processing times

        Parameters
        ----------
        MS : list[int]
            Machine selection part of the chromosome (length = total number of operations).
            For each operation, stores the index (within its feasible machine list) that was chosen.

        Returns
        -------
        JM : list[list[int]]
            JM[i][j] = machine index assigned to operation j of job i
        T : list[list[int]]
            T[i][j] = processing time of operation j of job i on that chosen machine
        """
        JM = []  # Machines selected for each operation
        T = []   # Processing times for those selected machines
        Ms_decompose = []  # Split MS by job (since MS is flattened across jobs)
        Site = 0

        # Split MS into per-job segments based on J (number of operations per job)
        for num_ops in self.J.values():
            Ms_decompose.append(MS[Site:Site + num_ops])
            Site += num_ops

        # Decode each job's operations
        for i in range(len(Ms_decompose)):  # i = job index (0-based)
            JM_i = []  # Selected machine indices for job i
            T_i = []   # Selected processing times for job i
            for j in range(len(Ms_decompose[i])):  # j = operation index within job i
                O_j = self.Processing_time[i][j]  # Feasible processing times for this operation
                M_ij = []  # Feasible machine indices
                T_ij = []  # Corresponding processing times

                # Build the list of feasible machines for this operation
                for Mac_num in range(len(O_j)):
                    if O_j[Mac_num] != 9999:
                        M_ij.append(Mac_num)
                        T_ij.append(O_j[Mac_num])

                # Pick the machine index and time corresponding to the selected position
                selected_index = Ms_decompose[i][j]
                JM_i.append(M_ij[selected_index])
                T_i.append(T_ij[selected_index])

            JM.append(JM_i)
            T.append(T_i)

        return JM, T

    # -------------------------------------------------------------------------
    # Compute the earliest feasible start time of an operation
    # -------------------------------------------------------------------------
    def Earliest_Start(self, Job, O_num, Machine):
        """
        Find the earliest possible start time for a given operation of a job on a selected machine,
        considering both machine availability and job precedence constraints.

        Parameters
        ----------
        Job : int
            Job index (0-based).
        O_num : int
            Operation index (0-based).
        Machine : int
            Machine index assigned for this operation.

        Returns
        -------
        tuple : (Start_time, Machine_id, Processing_time, Operation_id, Prev_op_end, End_time)
        """
        P_t = self.Processing_time[Job][O_num][Machine]  # Processing time on selected machine
        last_O_end = self.Jobs[Job].Last_Processing_end_time  # End time of previous operation
        Selected_Machine = Machine

        # Query the machine for its available empty time windows
        M_window = self.Machines[Selected_Machine].Empty_time_window()
        M_Tstart = M_window[0]  # list of window start times
        M_Tend = M_window[1]    # list of window end times
        M_Tlen = M_window[2]    # list of window durations
        Machine_end_time = self.Machines[Selected_Machine].End_time  # machine’s overall last end time

        # Default earliest start = after both machine and job are ready
        earliest_start = max(last_O_end, Machine_end_time)

        # Try to insert this operation into an available gap ("full insertion")
        if M_Tlen is not None:
            for le_i in range(len(M_Tlen)):
                # If a free window can fit this operation duration
                if M_Tlen[le_i] >= P_t:
                    # Case 1: Window starts after job is ready → start at window start
                    if M_Tstart[le_i] >= last_O_end:
                        earliest_start = M_Tstart[le_i]
                        break
                    # Case 2: Window overlaps job ready time but still fits
                    elif M_Tstart[le_i] < last_O_end and M_Tend[le_i] - last_O_end >= P_t:
                        earliest_start = last_O_end
                        break

        # Compute operation end time
        End_work_time = earliest_start + P_t
        return earliest_start, Selected_Machine, P_t, O_num, last_O_end, End_work_time

    # -------------------------------------------------------------------------
    # Decode a chromosome into an actual schedule and compute its fitness
    # -------------------------------------------------------------------------
    def decode(self, CHS, Len_Chromo):
        """
        Decode one chromosome into an actual schedule (job-machine timeline simulation).

        Parameters
        ----------
        CHS : list[int]
            Chromosome (combined MS + OS parts, total length = 2 * total_operations)
        Len_Chromo : int
            Split index between MS and OS parts (length of MS)

        Returns
        -------
        float
            Fitness value = makespan (maximum completion time across all jobs)
        """
        # Split chromosome into machine-selection (MS) and operation-sequence (OS)
        MS = list(CHS[0:Len_Chromo])
        OS = list(CHS[Len_Chromo:2 * Len_Chromo])

        # Get machine assignments and processing times from MS
        Needed_Matrix = self.Order_Matrix(MS)
        JM = Needed_Matrix[0]  # JM[j][o] = machine ID for operation o of job j

        # Process operations in the order given by OS
        for i in OS:
            Job = i  # current job index
            O_num = self.Jobs[Job].Current_Processed()  # operation number currently to be processed
            Machine = JM[Job][O_num]  # machine assigned for this operation

            # Determine earliest start time and finish time
            Para = self.Earliest_Start(Job, O_num, Machine)

            # Update job info (record operation completion)
            self.Jobs[Job]._Input(Para[0], Para[5], Para[1])

            # Update best fitness (makespan)
            if Para[5] > self.fitness:
                self.fitness = Para[5]

            # Update machine info (record the scheduled operation)
            self.Machines[Machine]._Input(Job, Para[0], Para[2], Para[3])

        # Return the final makespan as the fitness value
        return self.fitness
