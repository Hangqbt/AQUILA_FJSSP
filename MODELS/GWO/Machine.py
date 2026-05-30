class Machine_Time_window:
    def __init__(self, Machine_index):
        """
        Represents a single machine in the scheduling system.
        Tracks all operations assigned to this machine, including their
        start and end times, and dynamically maintains its time windows.

        Parameters
        ----------
        Machine_index : int
            Index (ID) of the machine.
        """
        self.Machine_index = Machine_index

        # ------------------------------
        # Machine scheduling records
        # ------------------------------
        self.assigned_task = []   # List of [job_id, operation_id] pairs assigned to this machine
        self.O_start = []         # Start times of the operations assigned
        self.O_end = []           # End times of the operations assigned
        self.End_time = 0         # The last (latest) finish time among all scheduled operations

    # -------------------------------------------------------------------------
    # Determine the machine's idle (empty) time windows
    # -------------------------------------------------------------------------
    def Empty_time_window(self):
        """
        Find all idle time windows on this machine based on its scheduled operations.

        Returns
        -------
        tuple (time_window_start, time_window_end, len_time_window)
            time_window_start : list
                Start times of idle intervals.
            time_window_end : list
                End times of idle intervals.
            len_time_window : list
                Durations (lengths) of each idle interval.

        Notes
        -----
        - Only considers internal idle windows between already scheduled operations.
        - Useful when deciding whether a new operation can be inserted into a gap.
        """
        time_window_start = []
        time_window_end = []
        len_time_window = []

        # Case 1: Machine has no recorded end times (no tasks yet)
        if self.O_end is None:
            pass

        # Case 2: Only one operation exists → possible gap before the first operation
        elif len(self.O_end) == 1:
            if self.O_start[0] != 0:
                time_window_start = [0]
                time_window_end = [self.O_start[0]]

        # Case 3: Multiple operations already assigned
        elif len(self.O_end) > 1:
            # If the first operation doesn’t start at time 0, the gap before it is also a window
            if self.O_start[0] != 0:
                time_window_start.append(0)
                time_window_end.append(self.O_start[0])

            # Build internal idle windows:
            # Between operation i’s end and operation i+1’s start
            time_window_start.extend(self.O_end[:-1])   # window starts at previous op's end
            time_window_end.extend(self.O_start[1:])    # window ends at next op's start

        # Calculate window lengths (duration of each idle period)
        if time_window_end is not None:
            len_time_window = [
                time_window_end[i] - time_window_start[i]
                for i in range(len(time_window_end))
            ]

        return time_window_start, time_window_end, len_time_window

    # -------------------------------------------------------------------------
    # Insert a new operation into the machine's schedule
    # -------------------------------------------------------------------------
    def _Input(self, Job, M_Ealiest, P_t, O_num):
        """
        Add a new operation to this machine’s schedule.

        Parameters
        ----------
        Job : int
            Index of the job to which this operation belongs (0-based).
        M_Ealiest : float or int
            Start time of the operation on this machine.
        P_t : float or int
            Processing time required for this operation.
        O_num : int
            Operation number within the job (0-based).

        Notes
        -----
        - The method decides whether to append the task to the end
          or insert it into an earlier time window (if feasible).
        - It keeps start/end times and assigned task lists sorted by time.
        """
        # If the machine already has scheduled operations
        if self.O_end != []:
            # If this operation starts earlier than the last scheduled one,
            # it may need to be inserted in the middle.
            if self.O_start[-1] > M_Ealiest:
                for i in range(len(self.O_end)):
                    # Find where the new operation fits chronologically
                    if self.O_start[i] >= M_Ealiest:
                        # Insert the task record at the correct position
                        self.assigned_task.insert(i, [Job + 1, O_num + 1])
                        break
            else:
                # Otherwise, append the task at the end (normal forward scheduling)
                self.assigned_task.append([Job + 1, O_num + 1])
        else:
            # First task ever assigned to this machine
            self.assigned_task.append([Job + 1, O_num + 1])

        # Record start and end times, keeping them sorted
        self.O_start.append(M_Ealiest)
        self.O_start.sort()
        self.O_end.append(M_Ealiest + P_t)
        self.O_end.sort()

        # Update the latest known finish time for the machine
        self.End_time = self.O_end[-1]
