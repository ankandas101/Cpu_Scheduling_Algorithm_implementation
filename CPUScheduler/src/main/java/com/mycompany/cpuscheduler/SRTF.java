
package com.mycompany.cpuscheduler;
import java.util.Arrays;
import java.util.Arrays;

import java.util.ArrayList;

public class SRTF {
    public void schedule(int[] arrivalTime, int[] burstTime) {
        int n = arrivalTime.length;
        int[] remainingTime = new int[n];
        int[] waitingTime = new int[n];
        int[] turnAroundTime = new int[n];
        boolean[] isCompleted = new boolean[n];

        ArrayList<Integer> processOrder = new ArrayList<>(); // For Gantt Chart
        ArrayList<Integer> timeOrder = new ArrayList<>();

        System.arraycopy(burstTime, 0, remainingTime, 0, n);
        int completed = 0, currentTime = 0, shortest = -1;
        boolean found;

        while (completed != n) {
            found = false;
            shortest = -1;

            // Find the process with the shortest remaining time that's already arrived
            for (int i = 0; i < n; i++) {
                if ((arrivalTime[i] <= currentTime) && !isCompleted[i]) {
                    if (shortest == -1 || remainingTime[i] < remainingTime[shortest]) {
                        shortest = i;
                        found = true;
                    }
                }
            }

            // If no process has arrived yet, increment time
            if (!found) {
                currentTime++;
                continue;
            }

            // Add the process to the Gantt chart if it's a new process or preempted
            if (processOrder.isEmpty() || processOrder.get(processOrder.size() - 1) != (shortest + 1)) {
                processOrder.add(shortest + 1);
                timeOrder.add(currentTime);
            }

            // Execute the process for 1 unit of time
            remainingTime[shortest]--;
            currentTime++;

            // If process is completed
            if (remainingTime[shortest] == 0) {
                completed++;
                isCompleted[shortest] = true;
                int finishTime = currentTime;
                waitingTime[shortest] = finishTime - burstTime[shortest] - arrivalTime[shortest];

                if (waitingTime[shortest] < 0) {
                    waitingTime[shortest] = 0;
                }

                turnAroundTime[shortest] = burstTime[shortest] + waitingTime[shortest];
            }
        }

        // Adding the last time to the timeOrder list
        timeOrder.add(currentTime);

        // Print Gantt Chart
        GanttChart.printGanttChart(processOrder.stream().mapToInt(i -> i).toArray(), timeOrder.stream().mapToInt(i -> i).toArray(), timeOrder.stream().mapToInt(i -> i).toArray());
        printTimes(waitingTime, turnAroundTime);
    }

    private void printTimes(int[] waitingTime, int[] turnAroundTime) {
        System.out.println("\nSRTF Scheduling:");
        System.out.println("Process | Waiting Time | Turnaround Time");

        for (int i = 0; i < waitingTime.length; i++) {
            System.out.println((i + 1) + "       | " + waitingTime[i] + "           | " + turnAroundTime[i]);
        }
    }
}
