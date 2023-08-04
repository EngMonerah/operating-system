import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CPUScheduling {

	private int processNum;
	private ArrayList<PCB> PCBs = new ArrayList<PCB>();
	private String prcessesChart = "";

	public CPUScheduling(int numOfPCBs) {
		this.processNum = 0;
		this.PCBs = new ArrayList<PCB>(numOfPCBs);
	}

	public void addPCB(int priority, int arrivalTime, int burstTime) {
		PCB pcb = new PCB((processNum + 1), priority, arrivalTime, burstTime);
		PCBs.add(pcb);
		processNum += 1;
	}

	public void run() {
		ArrayList<PCB> arrivalQueue = new ArrayList<PCB>();
		for (PCB p : PCBs) {
			p.startTime = -1;
			p.terminateTime = 0;
			p.waittingTime = 0;
			p.remaining = p.cpuBurst;
			arrivalQueue.add(p);
		}

		sortQueueByArrival(arrivalQueue);
		ArrayList<PCB> readyQueue = new ArrayList<PCB>();

		boolean contextSwitch = false;
		boolean cpuState = false;
		int cpuClock = 0;
		PCB cpuPCB = null;

		while (!arrivalQueue.isEmpty() || !readyQueue.isEmpty()
				|| cpuState == true) {
			while (!arrivalQueue.isEmpty()) {
				if (arrivalQueue.get(0).arrivalTime <= cpuClock) {
					PCB nextArrival = arrivalQueue.remove(0);
					readyQueue.add(nextArrival);
					sortQueueByPriority(readyQueue);
				} else {
					break;
				}
			}
			
			if (contextSwitch) {
				prcessesChart += " | CS";
				contextSwitch = false;
				
				if (cpuPCB != null) {
					readyQueue.add(cpuPCB);
					cpuState = false;
					cpuPCB = null;
				}
			}

			if (cpuState == false && !readyQueue.isEmpty()) {
				cpuState = true;
				cpuPCB = readyQueue.remove(0);
				prcessesChart += " | PN" + cpuPCB.id;
				if (cpuPCB.startTime < 0)
					cpuPCB.startTime = cpuClock;
			}

			if (cpuState == true && !readyQueue.isEmpty()) {
				if (readyQueue.get(0).priority < cpuPCB.priority) {
					//readyQueue.add(cpuPCB);
					//sortQueueByPriority(readyQueue);
					//cpuState = false;
					//cpuPCB = null;
					contextSwitch = true;
				}
			}

			cpuClock++;

			if (cpuPCB != null && !contextSwitch) {
				cpuPCB.remaining--;
				if (cpuPCB.remaining <= 0) {
					int wait = cpuClock - cpuPCB.arrivalTime - cpuPCB.cpuBurst;
					cpuPCB.waittingTime = wait;
					cpuPCB.terminateTime = cpuClock;

					cpuState = false;
					cpuPCB = null;
					contextSwitch = true;
					cpuClock++;
				}
			}

			
		}
	}

	private void sortQueueByArrival(ArrayList<PCB> q) {
		for (int i = 1; i < q.size(); i++) {
			for (int j = 0; j < (q.size() - i); j++) {
				if (q.get(j + 1).arrivalTime < q.get(j).arrivalTime) {
					PCB p = q.get(j);
					q.set(j, q.get(j + 1));
					q.set(j + 1, p);
				}
			}
		}
	}

	private void sortQueueByPriority(ArrayList<PCB> q) {
		for (int i = 1; i < q.size(); i++) {
			for (int j = 0; j < (q.size() - i); j++) {
				if ((q.get(j + 1).priority < q.get(j).priority)
						|| ((q.get(j + 1).priority == q.get(j).priority) && (q
								.get(j + 1).arrivalTime < q.get(j).arrivalTime))) {
					PCB p = q.get(j);
					q.set(j, q.get(j + 1));
					q.set(j + 1, p);
				}
			}
		}
	}

	public double getAvgTurnAroundTime() {
		double total = 0;
		for (PCB p : PCBs)
			total += p.getTurnAround();

		return total / PCBs.size();
	}

	public double getAvgWaitTime() {
		double total = 0;
		for (PCB p : PCBs)
			total += p.waittingTime;

		return total / PCBs.size();
	}

	public double getAvgResponseTime() {
		double total = 0;
		for (PCB p : PCBs)
			total += p.getResponseTime();

		return total / PCBs.size();
	}

	public void printReprot() {
		System.out.println();
		System.out.println("=================================================");
		System.out.println("| Detailed information about processes.");
		System.out.println("+--------------------------------------");
		for (PCB p : PCBs) {
			System.out.println(p);
			System.out.println();
		}

		System.out.println("=================================================");
		System.out.println("| Scheduling criteria.");
		System.out.println("+---------------------");

		System.out.println("Avg turnaround time = " + getAvgTurnAroundTime());
		System.out.println("Avg waiting time = " + getAvgWaitTime());
		System.out.println("Avg response time = " + getAvgResponseTime());
		System.out.println("=================================================");

		System.out.println("=================================================");
		System.out.println("| Processes Chart: " + prcessesChart);
		System.out.println("=================================================");

		PrintWriter pw = null;
		try {
			pw = new PrintWriter("Report.txt");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		if (pw != null) {
			pw.println();
			pw.println("=================================================");
			pw.println("| Detailed information about processes.");
			pw.println("+--------------------------------------");
			for (PCB p : PCBs) {
				pw.println(p);
				pw.println();
			}

			pw.println("=================================================");
			pw.println("| Scheduling criteria.");
			pw.println("+---------------------");

			pw.println("Avg turnaround time = " + getAvgTurnAroundTime());
			pw.println("Avg waiting time = " + getAvgWaitTime());
			pw.println("Avg response time = " + getAvgResponseTime());
			pw.println("=================================================");

			pw.println("=================================================");
			pw.println("| Processes Chart: " + prcessesChart);
			pw.println("=================================================");

			pw.close();
		}
	}

}
