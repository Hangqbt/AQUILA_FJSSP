class Job:
    def __init__(self, Job_index, Operation_num):
        """
        Represents a single job in the Flexible Job Shop Scheduling Problem (FJSP).

        Each job has multiple operations that must be processed sequentially.
        This class tracks which operations have been completed, when they started
        and ended, and on which machines they were processed.

        Parameters
        ----------
        Job_index : int
            Job ID (1-based index from the input instance).
        Operation_num : int
            Total number of operations for this job.
        """
        self.Job_index = Job_index
        self.Operation_num = Operation_num

        # ------------------------------
        # Job state tracking attributes
        # ------------------------------
        self.Processed = []       # Records how many operations have been processed (list of flags)
        self.J_start = []         # Start times of each operation
        self.J_end = []           # End times of each operation
        self.J_machine = []       # Machines used for each operation

        # ------------------------------
        # Most recent operation info
        # ------------------------------
        self.Last_Processing_Machine = None  # Machine used for the last processed operation
        self.Last_Processing_end_time = 0    # End time of the last processed operation

    # -------------------------------------------------------------------------
    # Returns the number of operations that have already been processed
    # -------------------------------------------------------------------------
    def Current_Processed(self):
        """
        Returns
        -------
        int
            The number of operations completed for this job so far.
            This is used to determine which operation index should be scheduled next.
        """
        return len(self.Processed)

    # -------------------------------------------------------------------------
    # Records a newly completed operation for this job
    # -------------------------------------------------------------------------
    def _Input(self, W_Eailiest, End_time, Machine):
        """
        Record the scheduling result of one operation.

        Parameters
        ----------
        W_Eailiest : float or int
            Start time of the current operation.
        End_time : float or int
            End time of the current operation.
        Machine : int
            Machine index on which this operation was processed.
        """
        # Update the most recent operation information
        self.Last_Processing_Machine = Machine
        self.Last_Processing_end_time = End_time

        # Mark one more operation as processed
        self.Processed.append(1)

        # Record operation timing and assigned machine
        self.J_start.append(W_Eailiest)
        self.J_end.append(End_time)
        self.J_machine.append(Machine)
