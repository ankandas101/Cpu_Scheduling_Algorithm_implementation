/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.cpuscheduler;

/**
 *
 * @author ankan
 */



public class SJF {
    public void schedule(int[] arrivalTime, int[] burstTime) {
        int n = arrivalTime.length;
        int[] waitingTime = new int[n];
        int[] turnAroundTime = new int[n];
        int[] startTime = new int[n];
        int[] endTime = new int[n];
        int[] processOrder = new int[n]; // Gantt chart process order

        boolean[] completed = new boolean[n];
        int currentTime = 0, completedProcesses = 0;

        while (completedProcesses < n) {
            int shortestJobIndex = -1;
            int minBurstTime = Integer.MAX_VALUE;

            // Find the shortest job that has arrived
            for (int i = 0; i < n; i++) {
                if (arrivalTime[i] <= currentTime && !completed[i] && burstTime[i] < minBurstTime) {
                    minBurstTime = burstTime[i];
                    shortestJobIndex = i;
                }
            }

            if (shortestJobIndex == -1) {
                currentTime++;
            } else {
                processOrder[completedProcesses] = shortestJobIndex + 1;  // Save process order for Gantt chart
                startTime[completedProcesses] = currentTime;
                waitingTime[shortestJobIndex] = currentTime - arrivalTime[shortestJobIndex];
                currentTime += burstTime[shortestJobIndex];
                endTime[completedProcesses] = currentTime;

                turnAroundTime[shortestJobIndex] = currentTime - arrivalTime[shortestJobIndex];
                completed[shortestJobIndex] = true;
                completedProcesses++;
            }
        }

        GanttChart.printGanttChart(processOrder, startTime, endTime);
        printTimes(waitingTime, turnAroundTime);
    }

    private void printTimes(int[] waitingTime, int[] turnAroundTime) {
        System.out.println("\nSJF Scheduling:");
        System.out.println("Process | Waiting Time | Turnaround Time");

        for (int i = 0; i < waitingTime.length; i++) {
            System.out.println((i + 1) + "       | " + waitingTime[i] + "           | " + turnAroundTime[i]);
        }
    }
}

