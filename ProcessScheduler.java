/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.osproject1;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */



/**
 *
 * @author ragha
 * 
 * 
 * What is this program doing?
It is simulating a Shortest Remaining Time First (SRTF) CPU scheduling algorithm.

- SRTF is a preemptive scheduling algorithm, meaning it always picks the process with the least
 remaining time left to execute.
- If a new process arrives with a shorter burst time than the currently running process, 
 the CPU switches to that new process.
- The program also considers context switches,
which take 1ms each time the CPU switches from one process to another.
 */

//package com.mycompany.processscheduler;

import java.util.*;

/*
Step-by-Step Breakdown:
1. Defining the Process class
The Process class represents a single process with attributes like:

id → Process ID
arrivalTime → When the process arrives
burstTime → Total execution time needed
remainingTime → How much execution time is still needed
completionTime → When the process finished execution
waitingTime → Total time the process spent waiting in the queue
turnaroundTime → Total time from arrival to completion
*/



public class ProcessScheduler {

    // Define the Process class to represent each process with its properties.
    static class Process {
        int id, arrivalTime, burstTime, remainingTime, completionTime, waitingTime, turnaroundTime;

        // Constructor to initialize the process with id, arrival time, and burst time.
        public Process(int id, int arrivalTime, int burstTime) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.remainingTime = burstTime; // Initially, remaining time is the same as burst time.
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Ask the user to input the number of processes.
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        // List to store the processes.
        List<Process> processes = new ArrayList<>();
        // Get the arrival and burst time for each process.
    
        System.out.println("\nEnter process details:");
        for (int i = 0; i < n; i++) {
            System.out.print("P" + (i + 1) + " Arrival Time : ");
            int arrivalTime = sc.nextInt();
            System.out.print("P" + (i + 1) + " Burst Time: ");
            int burstTime = sc.nextInt();
            processes.add(new Process(i + 1, arrivalTime, burstTime));
        }
        
        // Output in the desired format:
        System.out.print("\nNumber of processes=  " + n + "(");
        for (int i = 0; i < n; i++) {
            System.out.print("P" + (i + 1));
            if (i < n - 1) {
                System.out.print(",\t");
            }
        }
        System.out.println(")");
        
        System.out.println("Arrival times and burst times as follows:");
        for (Process p : processes) {
            System.out.println("P" + p.id + ": Arrival time = " + p.arrivalTime 
                    + ", Burst time = " + p.burstTime + " ms");
}
        // Print scheduling algorithm details.
        System.out.println("\nScheduling Algorithm: Shortest Remaining Time First (SRTF)");
        System.out.println("Context Switch: 1 ms\n");

        // Call the scheduling method to start the process scheduling.
        scheduleProcesses(processes);
    }

    public static void scheduleProcesses(List<Process> processes) {
        int currentTime = 0, completed = 0;
        int totalWaitingTime = 0, totalTurnaroundTime = 0;
        int cpuActiveTime = 0, idleTime = 0;

        // List to store the Gantt chart (timeline of processes execution).
        List<String> ganttChart = new ArrayList<>();

        
        /*
        
        4. Using a Priority Queue for Scheduling
- The program uses a Priority Queue (Min Heap) to always pick the process with the shortest remaining time.
- If two processes have the same remaining time, it selects the one that arrived first.
        */
        
        
        // PriorityQueue to select the process with the shortest remaining time.
        PriorityQueue<Process> pq = new PriorityQueue<>(
            (a, b) -> a.remainingTime == b.remainingTime ? a.arrivalTime - b.arrivalTime : a.remainingTime - b.remainingTime
        );

        
        /*
        
        3. Sorting Processes by Arrival Time
- Since SRTF scheduling starts with the earliest arriving process, we sort the processes based on arrival time.
        */
        
        
        // Sort processes by their arrival time.
        processes.sort(Comparator.comparingInt(p -> p.arrivalTime)); 
        int index = 0; // Index to track processes yet to be added to the priority queue.
        Process currentProcess = null; // The process that is currently being executed.

        
        /*
        5.1
        Adding Processes to the Queue
- Any process that has arrived at the current time is added to the pq.
        */
        
        
        
        // Main loop to simulate the scheduling until all processes are completed.
        while (completed < processes.size()) {

            
            /*
            5. The Scheduling Simulation\
The main logic for CPU scheduling runs in a while loop until all processes are completed.

Handling CPU Idle Time:
- If no process is available for execution (i.e., priority queue pq is empty), the CPU is idle.
- The CPU waits until the next process arrives.
            
            */
            
            
            
            // If no process is ready, but there are processes left to arrive, simulate idle time.
            if (pq.isEmpty() && index < processes.size() && processes.get(index).arrivalTime > currentTime) {
                ganttChart.add(currentTime + "-" + processes.get(index).arrivalTime + "  IDLE");
                idleTime += (processes.get(index).arrivalTime - currentTime); // Track idle time.
                currentTime = processes.get(index).arrivalTime; // Jump to the arrival time of the next process.
            }
            
            
            
            

            // Add all processes that have arrived at the current time to the priority queue.
            while (index < processes.size() && processes.get(index).arrivalTime <= currentTime) {
                pq.add(processes.get(index++));
            }

            
            
            /*
            5.2
            
            Selecting the Next Process
- The CPU picks the process with the shortest remaining time from the queue.
- If the CPU switches from one process to another, a context switch (1 ms delay) occurs.
            
            */
            
            
            
            
            // If there is a process ready to execute, schedule it.
            if (!pq.isEmpty()) {
                Process p = pq.poll(); // Get the process with the shortest remaining time.

                // If the current process changes, simulate a context switch (CS) of 1 ms.
                if (currentProcess != p && currentProcess != null) {
                    ganttChart.add(currentTime + "-" + (currentTime + 1) + "  CS");
                    currentTime++; // Context switch takes 1 ms.
                    idleTime++; // Context switch is considered idle time.
                }

                // Set the current process to the one that is being scheduled.
                currentProcess = p;
                ganttChart.add(currentTime + "-" + (currentTime + 1) + "  P" + p.id);
                p.remainingTime--; // Decrease the remaining time of the current process.
                currentTime++; // Move forward by 1 ms (the time it takes to execute the process).
                cpuActiveTime++; // Count this as active CPU time.

                
                /*
                
                5.3 
                Handling Process Completion:
- When a process finishes (remainingTime == 0), the program calculates:
- Completion time
- Turnaround time (completion - arrival)
- Waiting time (turnaround - burst)
- The completed process is not added back to the queue.


                
                */
                
                
                
                
                // If the process has finished execution, calculate its completion time, waiting time, and turnaround time.
                if (p.remainingTime == 0) {
                    p.completionTime = currentTime;
                    p.turnaroundTime = p.completionTime - p.arrivalTime;
                    p.waitingTime = p.turnaroundTime - p.burstTime;
                    totalWaitingTime += p.waitingTime;
                    totalTurnaroundTime += p.turnaroundTime;
                    completed++; // Mark this process as completed.
                } else {
                    pq.add(p); // If the process is not finished, re-add it to the queue.
                }
            }
        }

        
        /*
        6. Calculating CPU Utilization & Performance Metrics
- CPU Utilization measures how much time the CPU was actually working.
- Average Turnaround Time = Total turnaround time / Number of processes
- Average Waiting Time = Total waiting time / Number of processes
        
        
        
        */
        
        
        
        // Calculate total CPU utilization.
        int totalTime = currentTime;  // Total runtime of the simulation.
        double cpuUtilization = ((double) cpuActiveTime / totalTime) * 100; // Calculate the percentage of time the CPU was actively working.

        /*
        
        7. Printing the Gantt Chart and Performance Metrics
Gantt Chart (Process Timeline)
- Displays when each process ran and when the CPU was idle or switched.

        */
        
        
        
        // Output the Gantt chart showing the timeline of processes and context switches.
        System.out.println("\nGantt Chart:");
        for (String entry : ganttChart) {
            System.out.println(entry); // Print each entry in the Gantt chart.
        }

        // Print performance metrics in a formatted table.
        System.out.println("\nPerformance Metrics:");
        System.out.println("+------------------------+----------------+------------------+------------------+");
        System.out.println("| Metric                | Value (ms)     |");
        System.out.println("+------------------------+----------------+------------------+------------------+");
        System.out.printf("| Average Turnaround Time| %.2f           |\n", (double) totalTurnaroundTime / processes.size());
        System.out.printf("| Average Waiting Time   | %.2f           |\n", (double) totalWaitingTime / processes.size());
        System.out.printf("| CPU Utilization        | %.2f%%          |\n", cpuUtilization);
        System.out.println("+------------------------+----------------+------------------+------------------+");
    }
}


/*

Final Summary

1- Takes process details as input (arrival & burst time).
2- Sorts processes by arrival time.
3- Uses a priority queue to always pick the shortest remaining time process.
4- Handles context switching (adds a delay when switching between processes).
5- Handles CPU idle time (if no process is ready to run).
6- Calculates and prints CPU utilization, waiting time, and turnaround time.


*/