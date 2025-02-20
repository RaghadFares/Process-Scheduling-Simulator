# Process Scheduling Simulator - SRTF (Shortest Remaining Time First)

## **Team Members**  
- **[Team Leader Name]** - ID: [Your ID]  
- **[Raghad Almutairi]** - ID: [443200793]  
- **[Team Member 2]** - ID: [Their ID]  

## **Project Overview**  
This project simulates process scheduling using the **Shortest Remaining Time First (SRTF)** algorithm. The system calculates **average waiting time**, **average turnaround time**, and **CPU utilization**, and visualizes the execution of processes using a **Gantt chart**. The program also supports context switching and preemption.

## **Features**  
- **Shortest Remaining Time First (SRTF)** Scheduling Algorithm.
- **Context Switching**: Simulates a 1ms context switch time between processes.
- **Performance Metrics**: Calculates and outputs **Average Turnaround Time**, **Average Waiting Time**, and **CPU Utilization**.
- **Gantt Chart**: Displays the order and timing of processes in an easy-to-read format.

## **How to Run the Program**  
1. Clone this repository.
2. Compile the `ProcessScheduler.java` file using the command:
 3. Run the program using:


## **Input Format**  
- The program will prompt you to enter the number of processes.
- For each process, input the **arrival time** and **burst time**.

### Example Input:
Enter number of processes: 4 P1 Arrival Time: 0 P1 Burst Time: 8 P2 Arrival Time: 1 P2 Burst Time: 4 P3 Arrival Time: 2 P3 Burst Time: 5 P4 Arrival Time: 3 P4 Burst Time: 5


## **Expected Output**  
The program will output the **Gantt chart**, showing the execution timeline of the processes, and the **performance metrics** in a table format.

### Example Output:
Gantt Chart: 0-1 P1 1-2 CS 2-6 P2 6-7 CS 7-12 P3 12-13 CS 13-18 P4 18-19 CS 19-26 P1

Performance Metrics: +------------------------+----------------+------------------+------------------+ | Metric | Value (ms) | +------------------------+----------------+------------------+------------------+ | Average Turnaround Time| 14.00 | | Average Waiting Time | 8.50 | | CPU Utilization | 84.62% | +------------------------+----------------+------------------+------------------+


## **Known Issues**  
- **Input validation** for negative values is not yet implemented.  
- **Edge case** handling for simultaneous process arrivals could be improved.

## **Task Distribution**  
| Team Member         | Tasks Assigned                        |
|---------------------|---------------------------------------|
| [Team Leader Name]   | **Lead Developer**, Scheduler logic  |
| [Raghad Almutairi]      | Process handling, Gantt chart logic   |
| [Team Member
