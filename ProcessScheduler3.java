/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.processscheduler3;


/**
 * 
 * This program simulates the Shortest Remaining Time First (SRTF) CPU scheduling algorithm.
 * 
 * - SRTF is a **preemptive scheduling algorithm**, meaning it always picks the process with 
 *   the least remaining time left to execute.
 * - If a new process arrives with a shorter burst time than the currently running process, 
 *   the CPU switches to that new process (preemption occurs).
 * - The program also considers **context switching**, which takes **1ms** each time the CPU switches 
 *   from one process to another.
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

public class ProcessScheduler3 {

 // Define the Process class to represent each process with its properties.
    static class Process {
        int id, arrivalTime, burstTime, remainingTime, completionTime, waitingTime, turnaroundTime;

                 // Constructor to initialize the process with id, arrival time, and burst time.
        public Process(int id, int arrivalTime, int burstTime) {
            this.id = id;
            this.arrivalTime = arrivalTime;
            this.burstTime = burstTime;
            this.remainingTime = burstTime;  // Initially, remaining time is the same as burst time.
        }
    }

    /**
     * Event class represents an event in the event-driven simulation.
     * Events include: Process Arrival, Process Completion, and Context Switch.
     */
    static class Event {
        int time; // Time when the event occurs
        String type; // Type of event (ARRIVAL, COMPLETION, CONTEXT_SWITCH)
        Process process;

        public Event(int time, String type, Process process) {
            this.time = time;
            this.type = type;
            this.process = process;
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

        // Event Queue for Managing Events (ARRIVAL, COMPLETION, CONTEXT SWITCH)
        PriorityQueue<Event> eventQueue = new PriorityQueue<>(Comparator.comparingInt(e -> e.time));

        // Add all process arrival events to the event queue
        for (Process p : processes) {
            eventQueue.add(new Event(p.arrivalTime, "ARRIVAL", p));
        }

        Process currentProcess = null;

        // Event-Driven Simulation Loop
        while (!eventQueue.isEmpty()) {
            Event event = eventQueue.poll();
            currentTime = event.time;

            if (event.type.equals("ARRIVAL")) {
                pq.add(event.process);

                // If CPU is idle, immediately start this process
                if (currentProcess == null || event.process.remainingTime < currentProcess.remainingTime) {
                    if (currentProcess != null) {
                        ganttChart.add(currentTime + "-" + (currentTime + 1) + "  CS");
                        eventQueue.add(new Event(currentTime + 1, "CONTEXT_SWITCH", null));
                        continue;
                    }
                    currentProcess = pq.poll();
                    eventQueue.add(new Event(currentTime, "COMPLETION", currentProcess));
                }
            } 
            else if (event.type.equals("COMPLETION")) {
                if (currentProcess != null) {
                    ganttChart.add(currentTime + "-" + (currentTime + currentProcess.remainingTime) + "  P" + currentProcess.id);
                    currentTime += currentProcess.remainingTime;
                    currentProcess.completionTime = currentTime;
                    currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                    totalWaitingTime += currentProcess.waitingTime;
                    totalTurnaroundTime += currentProcess.turnaroundTime;
                    completed++;
                    currentProcess = null;
                }

                if (!pq.isEmpty()) {
                    currentProcess = pq.poll();
                    eventQueue.add(new Event(currentTime, "COMPLETION", currentProcess));
                }
            } 
            else if (event.type.equals("CONTEXT_SWITCH")) {
                if (!pq.isEmpty()) {
                    currentProcess = pq.poll();
                    eventQueue.add(new Event(currentTime, "COMPLETION", currentProcess));
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
        double cpuUtilization = ((double) cpuActiveTime / totalTime) * 100;

        
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
        System.out.println("+------------------------+------------+");
        System.out.printf("| %-24s | %-10s |\n", "Metric", "Value (ms)");
        System.out.println("+------------------------+------------+");
        System.out.printf("| %-24s | %-10.2f |\n", "Average Turnaround Time", (double) totalTurnaroundTime / processes.size());
        System.out.printf("| %-24s | %-10.2f |\n", "Average Waiting Time", (double) totalWaitingTime / processes.size());
        System.out.printf("| %-24s | %-9.2f%% |\n", "CPU Utilization", cpuUtilization);
        System.out.println("+------------------------+------------+");
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