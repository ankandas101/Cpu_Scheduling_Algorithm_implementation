/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpuscheduler;
import java.util.Arrays;
/**
 *
 * @author ankan
 */public class RR {
    public void schedule(int[] arrivalTime, int[] burstTime, int quantum) {
        int n = arrivalTime.length;
        int[] waitingTime = new int[n];
        int[] turnAroundTime = new int[n];
        int[] remainingBurstTime = Arrays.copyOf(burstTime, n);
        int[] completionTime = new int[n];
        boolean[] isCompleted = new boolean[n];

        int currentTime = 0, completed = 0;

        while (completed != n) {
            boolean allDone = true;

            for (int i = 0; i < n; i++) {
                if (!isCompleted[i] && arrivalTime[i] <= currentTime) {
                    allDone = false;
                    if (remainingBurstTime[i] > quantum) {
                        currentTime += quantum;
                        remainingBurstTime[i] -= quantum;
                    } else {
                        currentTime += remainingBurstTime[i];
                        completionTime[i] = currentTime;
                        remainingBurstTime[i] = 0;
                        isCompleted[i] = true;
                        completed++;
                        turnAroundTime[i] = completionTime[i] - arrivalTime[i];
                        waitingTime[i] = turnAroundTime[i] - burstTime[i];
                    }
                }
            }

            if (allDone) {
                currentTime++;
            }
        }

        // Print Gantt Chart
        GanttChart.printGanttChart(new int[]{1, 2, 3}, new int[]{0, 2, 4}, new int[]{2, 4, 7});

        // Print Waiting and Turnaround times
        printTimes(waitingTime, turnAroundTime);

        // Calculate and Print Average Times
        Utility.calculateAndPrintAverageTimes(waitingTime, turnAroundTime);
    }

    private void printTimes(int[] waitingTime, int[] turnAroundTime) {
        System.out.println("\nRR Scheduling:");
        System.out.println("Process | Waiting Time | Turnaround Time");

        for (int i = 0; i < waitingTime.length; i++) {
            System.out.println((i + 1) + "       | " + waitingTime[i] + "           | " + turnAroundTime[i]);
        }
    }
}
