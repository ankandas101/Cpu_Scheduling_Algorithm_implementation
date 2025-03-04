
package com.mycompany.cpuscheduler;
/**
 *
 * @author ankan
 */

import java.util.ArrayList;

public class PriorityPreemptive {
    public void schedule(int[] arrivalTime, int[] burstTime, int[] priority) {
        int n = arrivalTime.length;
        int[] remainingTime = new int[n];
        int[] waitingTime = new int[n];
        int[] turnAroundTime = new int[n];
        boolean[] isCompleted = new boolean[n];

        ArrayList<Integer> processOrder = new ArrayList<>(); // For Gantt Chart
        ArrayList<Integer> timeOrder = new ArrayList<>();

        System.arraycopy(burstTime, 0, remainingTime, 0, n);
        int completed = 0, currentTime = 0, highestPriority = -1;
        boolean found;

        while (completed != n) {
            found = false;
            highestPriority = -1;

            // Find the highest priority process that has already arrived and is not completed
            for (int i = 0; i < n; i++) {
                if (arrivalTime[i] <= currentTime && !isCompleted[i]) {
                    if (highestPriority == -1 || priority[i] < priority[highestPriority]) {
                        highestPriority = i;
                        found = true;
                    }
                }
            }

            // If no process is available, increment time
            if (!found) {
                currentTime++;
                continue;
            }

            // Add the process to the Gantt chart if it is new or preempted
            if (processOrder.isEmpty() || processOrder.get(processOrder.size() - 1) != (highestPriority + 1)) {
                processOrder.add(highestPriority + 1);
                timeOrder.add(currentTime);
            }

            // Execute the highest priority process for 1 time unit
            remainingTime[highestPriority]--;
            currentTime++;

            // If the process is completed
            if (remainingTime[highestPriority] == 0) {
                completed++;
                isCompleted[highestPriority] = true;
                int finishTime = currentTime;
                waitingTime[highestPriority] = finishTime - burstTime[highestPriority] - arrivalTime[highestPriority];

                if (waitingTime[highestPriority] < 0) {
                    waitingTime[highestPriority] = 0;
                }

                turnAroundTime[highestPriority] = burstTime[highestPriority] + waitingTime[highestPriority];
            }
        }

        // Adding the last time to the timeOrder list
        timeOrder.add(currentTime);

        // Print Gantt Chart
        GanttChart.printGanttChart(processOrder.stream().mapToInt(i -> i).toArray(), timeOrder.stream().mapToInt(i -> i).toArray(), timeOrder.stream().mapToInt(i -> i).toArray());
        printTimes(waitingTime, turnAroundTime);
    }

    private void printTimes(int[] waitingTime, int[] turnAroundTime) {
        System.out.println("\nPreemptive Priority Scheduling:");
        System.out.println("Process | Waiting Time | Turnaround Time");

        for (int i = 0; i < waitingTime.length; i++) {
            System.out.println((i + 1) + "       | " + waitingTime[i] + "           | " + turnAroundTime[i]);
        }
    }
}
