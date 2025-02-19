# Operating-system-

# Process Scheduling Simulator  

## Overview  
This project implements a **Shortest Remaining Time First (Preemptive SJF) Scheduling Algorithm**, using **FCFS for tie-breaking**. It simulates process execution in a **multiprogramming environment** with **event-driven scheduling**.  

## Features  
- **Preemptive SJF Scheduling** with context switching (**1ms overhead**).  
- **Event Queue** for handling process arrivals, execution, and termination.  
- **Performance Metrics**:  
  - **CPU Utilization**  
  - **Average Turnaround Time**  
  - **Average Waiting Time**  
- **Gantt Chart Visualization**.  
- **User Input**: Number of processes, arrival times, and burst times.  

## Output Example  
```
Gantt Chart: | P1 | P2 | P3 | P2 | P1 |
CPU Utilization: 92.3%
Avg Turnaround Time: 5.67 ms
Avg Waiting Time: 2.67 ms
```

## Requirements  
- **Input:** Process count, arrival times, burst times.  
- **Algorithm:** Preemptive SJF with FCFS tie-breaking.  
- **Output:** Scheduling results & performance metrics.  


