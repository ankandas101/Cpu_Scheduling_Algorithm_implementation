
package com.mycompany.cpuscheduler;
public class FCFS {
    public void schedule(int[] arrivalTime, int[] burstTime) {
        int n = arrivalTime.length;
        int[] waitingTime = new int[n];
        int[] turnAroundTime = new int[n];
        int[] startTime = new int[n];
        int[] endTime = new int[n];
        int[] processOrder = new int[n]; // Store process order for Gantt Chart

        waitingTime[0] = 0;
        startTime[0] = arrivalTime[0];
        endTime[0] = startTime[0] + burstTime[0];
        processOrder[0] = 1;

        for (int i = 1; i < n; i++) {
            startTime[i] = Math.max(endTime[i - 1], arrivalTime[i]);
            endTime[i] = startTime[i] + burstTime[i];
            processOrder[i] = i + 1;

            waitingTime[i] = startTime[i] - arrivalTime[i];
            turnAroundTime[i] = waitingTime[i] + burstTime[i];
        }

        GanttChart.printGanttChart(processOrder, startTime, endTime);
        printTimes(waitingTime, turnAroundTime);
    }

    private void printTimes(int[] waitingTime, int[] turnAroundTime) {
        System.out.println("\nFCFS Scheduling:");
        System.out.println("Process | Waiting Time | Turnaround Time");

        for (int i = 0; i < waitingTime.length; i++) {
            System.out.println((i + 1) + "       | " + waitingTime[i] + "           | " + turnAroundTime[i]);
        }
    }
}
