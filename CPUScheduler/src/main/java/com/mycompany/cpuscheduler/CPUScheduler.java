package com.mycompany.cpuscheduler;
/**
 *
 * @author ankan
 */
import java.util.Scanner;

public class CPUScheduler {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Input number of processes
        System.out.println("Enter the number of processes:");
        int n = sc.nextInt();

        // Initialize arrays for arrival time, burst time, and priority
        int[] arrivalTime = new int[n];
        int[] burstTime = new int[n];
        int[] priority = new int[n];  // For Priority Scheduling

        // Input Arrival Time, Burst Time, and Priority for each process
        System.out.println("Enter Arrival Time, Burst Time, and Priority for each process:");
        for (int i = 0; i < n; i++) {
            System.out.print("Process " + (i + 1) + " Arrival Time: ");
            arrivalTime[i] = sc.nextInt();
            System.out.print("Process " + (i + 1) + " Burst Time: ");
            burstTime[i] = sc.nextInt();
            System.out.print("Process " + (i + 1) + " Priority: ");
            priority[i] = sc.nextInt();  // Priority input
        }

        // Continue running algorithms until the user chooses to exit
        boolean exit = false;

        while (!exit) {
            // Display algorithm options
            System.out.println("\nChoose Scheduling Algorithm: ");
            System.out.println("1. FCFS");
            System.out.println("2. SJF");
            System.out.println("3. SRTF");
            System.out.println("4. RR");
            System.out.println("5. Preemptive Priority Scheduling");
            System.out.println("6. Non-Preemptive Priority Scheduling");
            System.out.println("7. Exit");

            // Get user's choice
            int choice = sc.nextInt();

            // Switch case to call the selected algorithm
            switch (choice) {
                case 1:
                    FCFS fcfs = new FCFS();
                    fcfs.schedule(arrivalTime, burstTime);
                    break;
                case 2:
                    SJF sjf = new SJF();
                    sjf.schedule(arrivalTime, burstTime);
                    break;
                case 3:
                    SRTF srtf = new SRTF();
                    srtf.schedule(arrivalTime, burstTime);
                    break;
                case 4:
                    System.out.print("Enter Quantum Time: ");
                    int quantum = sc.nextInt();
                    RR rr = new RR();
                    rr.schedule(arrivalTime, burstTime, quantum);
                    break;
                case 5:
                    PriorityPreemptive priorityPreemptive = new PriorityPreemptive();
                    priorityPreemptive.schedule(arrivalTime, burstTime, priority);
                    break;
                case 6:
                    PriorityNonPreemptive priorityNonPreemptive = new PriorityNonPreemptive();
                    priorityNonPreemptive.schedule(arrivalTime, burstTime, priority);
                    break;
                case 7:
                    exit = true;
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid Choice, please select a valid option.");
            }

            // Ask the user if they want to run another algorithm with the same inputs
            if (!exit) {
                System.out.println("\nDo you want to run another algorithm with the same input? (y/n): ");
                char runAgain = sc.next().charAt(0);
                if (runAgain == 'n' || runAgain == 'N') {
                    exit = true;
                    System.out.println("Exiting the program.");
                }
            }
        }

        // Close the scanner
        sc.close();
    }
}
