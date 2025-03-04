
package com.mycompany.cpuscheduler;

/**
 *
 * @author ankan
 */

import java.util.Arrays;

public class PriorityNonPreemptive {
    public void schedule(int[] arrivalTime, int[] burstTime, int[] priority) {
        int n = arrivalTime.length;
        int[] waitingTime = new int[n];
        int[] turnAroundTime = new int[n];
        boolean[] isCompleted = new boolean[n];

        int currentTime = 0, completed = 0;

        // Initialize Gantt Chart
        int[] processOrder = new int[n];
        int[] startTime = new int[n];
        int[] finishTime = new int[n];
        int ganttIndex = 0;

        while (completed != n) {
            // Find the highest priority process that has arrived and is not completed
            int highestPriority = -1;

            for (int i = 0; i < n; i++) {
                if (arrivalTime[i] <= currentTime && !isCompleted[i]) {
                    if (highestPriority == -1 || priority[i] < priority[highestPriority]) {
                        highestPriority = i;
                    }
                }
            }

            if (highestPriority == -1) {
                currentTime++;
                continue;
            }

            // Schedule the process
            processOrder[ganttIndex] = highestPriority + 1;
            startTime[ganttIndex] = currentTime;
            currentTime += burstTime[highestPriority];
            finishTime[ganttIndex] = currentTime;
            ganttIndex++;

            // Mark process as completed
            isCompleted[highestPriority] = true;
            completed++;

            // Calculate waiting and turnaround times
            waitingTime[highestPriority] = currentTime - arrivalTime[highestPriority] - burstTime[highestPriority];
            if (waitingTime[highestPriority] < 0) waitingTime[highestPriority] = 0;

            turnAroundTime[highestPriority] = burstTime[highestPriority] + waitingTime[highestPriority];
        }

        // Display Gantt Chart
        GanttChart.printGanttChart(
                Arrays.copyOf(processOrder, ganttIndex),
                Arrays.copyOf(startTime, ganttIndex),
                Arrays.copyOf(finishTime, ganttIndex)
        );

        // Print Waiting and Turnaround times
        printTimes(waitingTime, turnAroundTime);
    }

    private void printTimes(int[] waitingTime, int[] turnAroundTime) {
        System.out.println("\nNon-Preemptive Priority Scheduling:");
        System.out.println("Process | Waiting Time | Turnaround Time");

        for (int i = 0; i < waitingTime.length; i++) {
            System.out.println((i + 1) + "       | " + waitingTime[i] + "           | " + turnAroundTime[i]);
        }
    }
}
