
package com.mycompany.cpuscheduler;

/**
 *
 * @author ankan
 */
// Helper method to display the Gantt Chart
public class GanttChart {
    public static void printGanttChart(int[] processOrder, int[] startTime, int[] endTime) {
        System.out.println("\nGantt Chart:");
        System.out.print("|");
        for (int i = 0; i < processOrder.length; i++) {
            System.out.print(" P" + processOrder[i] + " |");
        }
        System.out.println();

        System.out.print(startTime[0]);
        for (int i = 0; i < endTime.length; i++) {
            System.out.print("     " + endTime[i]);
        }
        System.out.println();
    }
}
