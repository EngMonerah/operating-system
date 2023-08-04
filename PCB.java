
public class PCB {

	public int id;
	public int priority;
	public int cpuBurst;
	public int arrivalTime;
	public int startTime;
	public int terminateTime;
	public int waittingTime;	
	public int remaining;

    public PCB(int id, int priority, int arrivalTime, int cpuBurstTime) {
        this.id = id;
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.cpuBurst = cpuBurstTime;
        this.startTime = 0;
        this.terminateTime = 0;
        this.waittingTime = 0; 
        this.remaining = cpuBurstTime;
    }
    
    public int getTurnAround() {
        return waittingTime + cpuBurst;
    }
    
    public int getResponseTime() {
        return startTime - arrivalTime;
    }
	
	public String toString() {
		return "Process ID: PN" + id +
				"\n  Priority: " + priority +
				"\n  CPU burst: " + cpuBurst +
				"\n  Arrival Time: " + arrivalTime +
				"\n  Start Time: " + startTime +
				"\n  Termination Time: " + terminateTime +
				"\n  Turn around Time: " + getTurnAround() +
				"\n  Waiting Time: " + waittingTime +
				"\n  Response Time: " + getResponseTime();
	}
}
