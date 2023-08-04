
import java.util.Scanner;

public class MainClass {
	public static void main(String[] args) {
		Scanner scr = new Scanner(System.in);

		CPUScheduling cpuScheduling = null;
		
		while (true) {
			System.out.println("| Main Menu");
			System.out.println("+------------------------------+");
			System.out.println("1. Initialize processes");
			System.out.println("2. Report");
			System.out.println("3. Exit");
			System.out.println("+------------------------------+");
			System.out.print("Enter Choice [1-3]:");
			int choice;
			choice = Integer.parseInt(scr.next());
			if (choice == 1) {
				System.out.print("Enter number of processes: ");
				int numOfPCBs = scr.nextInt();
				cpuScheduling = new CPUScheduling(numOfPCBs);
				
				System.out.println("Enter priority, arrival time and CPU burst of each process.");
				for (int i = 0; i < numOfPCBs; i++) {					
					System.out.println("Process [PN" + (i+1) + "]:");
					System.out.print("  Priority: ");
					int priority = scr.nextInt();
					
					while (priority < 0 || priority > 5) {
						System.out.println("Priority must be in range 1 to 5.");
						System.out.print("  Priority: ");
						priority = scr.nextInt();
					}
					
					System.out.print("  Arrival Time: ");
					int arrivalTime = scr.nextInt();
					
					System.out.print("  CPU burst Time: ");
					int cpuBurstTime = scr.nextInt();
					
					cpuScheduling.addPCB(priority, arrivalTime, cpuBurstTime);
				}
			} else if (choice == 2) {
				if (cpuScheduling == null) {
					System.out.println("You must initialize processes first.");
				} else {
					cpuScheduling.run();
					cpuScheduling.printReprot();
				}
				
			} else if (choice == 3) {
				break;
			} else {
				System.out.println("Invalid choise.");
			}
			System.out.println();
		}
		
		scr.close();
	}
}
