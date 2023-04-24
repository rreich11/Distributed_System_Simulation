package finalAssignment;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class MasterReadFromSlaveA extends Thread{
	
	private ObjectInputStream readObjectFromSlaveA;
	private ArrayList<Job>completedJobs1;
	private ArrayList<Job>completedJobs2;
	private SlaveCounter counterA;
	private SlaveCounter counterB;

		
	public MasterReadFromSlaveA(ObjectInputStream readObjectFromSlaveA, ArrayList<Job>completedJobs1, ArrayList<Job>completedJobs2, SlaveCounter counterA, SlaveCounter counterB) {
		this.readObjectFromSlaveA = readObjectFromSlaveA;
		this.completedJobs1 = completedJobs1;
		this.completedJobs2 = completedJobs2;
		this.counterA = counterA;
		this.counterB = counterB;

	}
	
	@Override
	public void run() {
		Job finishedJob;
		try {
			
			//read in first completed job from slaveA
			finishedJob = (Job) readObjectFromSlaveA.readObject();
			while(true) {
			System.out.println("Master received " + finishedJob + " from slave A"); 	
			System.out.println();
	
			//as long as there are jobs coming in loop
			if(finishedJob != null) {
				//if job is from client 1, add to array of completedJobs1
				if(finishedJob.getClientID() == 1) {
					synchronized(completedJobs1) {
						completedJobs1.add(finishedJob);
					}
				}
				//if job is from client 2, add to array of completedJobs2
				else {
					synchronized(completedJobs2) {
						completedJobs2.add(finishedJob);
					}
				}
				System.out.println("Completed Jobs list 1: ");
				System.out.println(completedJobs1);
				

				System.out.println("Completed Jobs list 2: ");
				System.out.println(completedJobs2);
				System.out.println();

				System.out.println(finishedJob + " completed by slave A. \nSending back to client " + finishedJob.getClientID());
				System.out.println();

				//if jobtype is A, remove 2 from counterA
				if(finishedJob.getjType() == JobType.A) {
					synchronized(counterA) {
						counterA.removeFromCounter(2);
					}
					System.out.println("2 removed from slave A counter ");
					System.out.println();

				}
				//if jobtype is B, remove 10 from counterB
				else {
					synchronized(counterB) {
						counterB.removeFromCounter(10);
					}
					System.out.println("10 removed from slave B counter ");
					System.out.println();


				}			
				//read the next finished job
				finishedJob = (Job) readObjectFromSlaveA.readObject();
			}
			else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			}
		}
		catch (ClassNotFoundException e) {
			System.err.println("Error, class not found ");
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}

