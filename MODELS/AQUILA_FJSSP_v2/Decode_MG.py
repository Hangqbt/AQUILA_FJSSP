import numpy as np
from Job import Job
from Machine import Machine_Time_window


class Decode:
    def __init__(self, J, Processing_time, M_num):
        self.Processing_time = Processing_time
        self.M_num = M_num
        self.J = J

        self.Machines = []
        self.Scheduled = []
        self.fitness = 0
        self.Machine_State = np.zeros(M_num, dtype=int)
        self.Jobs = []

        for j in range(M_num):
            self.Machines.append(Machine_Time_window(j))
        for k, v in J.items():
            self.Jobs.append(Job(k, v))

    def Order_Matrix(self, MS):
        JM = []
        for i in range(len(self.J)):
            JM_i = []
            for j in range(self.J[i + 1]):
                JM_i.append(self.Processing_time[i][j].index(min(self.Processing_time[i][j])))
            JM.append(JM_i)

        O_num = 0
        for i in range(len(JM)):
            for j in range(len(JM[i])):
                if MS[O_num] != JM[i][j]:
                    T_M = [idx for idx, val in enumerate(self.Processing_time[i][j]) if val != 9999]
                    JM[i][j] = T_M[MS[O_num]]
                O_num += 1
        return JM, self.Processing_time

    def Earliest_Start(self, Job, O_num, Machine):
        P_t = self.Processing_time[Job][O_num][Machine]
        last_O_end = self.Jobs[Job].Last_Processing_end_time
        Selected_Machine = self.Machines[Machine]
        M_window = Selected_Machine.Empty_time_window()
        M_Tstart = M_window[0]
        M_Tend = M_window[1]
        M_Tlen = M_window[2]

        Machine_end_time = Selected_Machine.End_time
        Early_S = max(last_O_end, Machine_end_time)

        if M_Tlen:
            for i in range(len(M_Tlen)):
                if M_Tlen[i] >= P_t and M_Tstart[i] >= last_O_end:
                    Early_S = M_Tstart[i]
                    break
                if M_Tstart[i] < last_O_end and M_Tend[i] - last_O_end >= P_t:
                    Early_S = last_O_end
                    break

        Selected_Machine._Input(Job, Early_S, P_t, O_num)
        return Early_S, Machine, P_t, O_num, Job, Early_S + P_t

    def decode(self, CHS, Len_Chromo):
        MS = list(CHS[0:Len_Chromo])
        OS = list(CHS[Len_Chromo:2 * Len_Chromo])

        Needed_Matrix = self.Order_Matrix(MS)
        JM = Needed_Matrix[0]

        for i in OS:
            Job = i
            O_num = self.Jobs[Job].Current_Processed()
            Machine = JM[Job][O_num]

            Para = self.Earliest_Start(Job, O_num, Machine)

            self.Jobs[Job]._Input(Para[0], Para[5], Para[1])

            if Para[5] > self.fitness:
                self.fitness = Para[5]

            self.Machine_State[Machine] = 1

        return self.fitness

    # =========================================================================
    # NEW CRITICAL PATH INVESTIGATOR FOR THE MG NEIGHBORHOOD
    # =========================================================================
    def get_critical_path(self):
        """
        Traces the exact sequence of operations that dictate the makespan.
        Returns a list of tuples: (Job_ID, Operation_ID, Machine_ID)
        *Note: IDs returned are 0-based indexing for array compatibility.
        """
        makespan = self.fitness
        current_job = -1
        current_op = -1

        # 1. Find the operation that finished exactly at the Makespan
        for j_idx, job in enumerate(self.Jobs):
            if job.J_end and max(job.J_end) == makespan:
                current_job = j_idx
                current_op = job.J_end.index(makespan)
                break

        critical_path = []

        # 2. Trace backwards to Time = 0
        while current_job != -1 and current_op != -1:
            machine_idx = self.Jobs[current_job].J_machine[current_op]
            critical_path.append((current_job, current_op, machine_idx))

            start_time = self.Jobs[current_job].J_start[current_op]

            if start_time == 0:
                break  # We successfully traced back to the beginning of time

            # Question A: Was it waiting for the previous operation of the SAME JOB?
            if current_op > 0 and self.Jobs[current_job].J_end[current_op - 1] == start_time:
                current_op = current_op - 1
                continue

            # Question B: Was it waiting for the SAME MACHINE to finish a different job?
            machine = self.Machines[machine_idx]
            found_pred = False
            for prev_job, prev_op in machine.assigned_task:
                pj_0 = prev_job - 1
                po_0 = prev_op - 1
                if self.Jobs[pj_0].J_end[po_0] == start_time:
                    current_job = pj_0
                    current_op = po_0
                    found_pred = True
                    break

            if not found_pred:
                # Break failsafe for floating-point gap insertion weirdness
                break

        # Reverse it so it reads from Time 0 -> Makespan
        return critical_path[::-1]