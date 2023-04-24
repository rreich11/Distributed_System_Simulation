package finalAssignment;


import java.io.*;
import java.util.*;

public class masterWriteToSlaveThread extends Thread{

	private ObjectOutputStream writeToSlaveA;
	private ObjectOutputStream writeToSlaveB;
	private ArrayList<Job> jobArray;
	private SlaveCounter counterA;
	private SlaveCounter counterB;

	
	
	public masterWriteToSlaveThread(ObjectOutputStream writeToSlaveA, ObjectOutputStream writeToSlaveB, ArrayList<Job> array, 
			SlaveCounter counterA, SlaveCounter counterB) {
		this.writeToSlaveA = writeToSlaveA;
		this.writeToSlaveB = writeToSlaveB;
		this.jobArray = array;
		this.counterA = counterA;
		this.counterB = counterB;
	}
	
	@Override
	public void run() {

		while(true) {	

			//as long as jobArray is not empty
			if(!jobArray.isEmpty()) {
				
				System.out.println("Slave Counter A: " + counterA.getCount() + "\tSlave counter B: " + counterB.getCount());
				System.out.println();

				
				//if the job is type A
				if(jobArray.get(0).getjType() == JobType.A) {
					
					//if sending optimal job to slaveA is less than sending to slaveB
					if(counterA.getCount() + 2 <= counterB.getCount() + 10) {
						//synchronize global counter, and then add appropriately
						synchronized(counterA) {
							counterA.addToCounter(2);
						}	
						try {
							//write job object to slave A
							writeToSlaveA.writeObject(jobArray.get(0));
						} catch (IOException e) {
							e.printStackTrace();
						}
						System.out.println("Master writing job to slave A: " + jobArray.get(0));
						System.out.println();

					}
					//if it makes more sense to send job A to slaveB
					else {
						//synchronize global counter, and then add appropriately
						synchronized(counterB) {
							counterB.addToCounter(10);
						}
						//write job obj to slaveB
						try {
							writeToSlaveB.writeObject(jobArray.get(0));
						} catch (IOException e) {
							e.printStackTrace();
						}
						System.out.println("Master writing job to slave B:  " + jobArray.get(0));
						System.out.println();

					}
				}

				//if job is type B
				else {
				
					//if sending jobB to slaveB is more optimal than sending to slaveA
					if(counterB.getCount() + 2 <= counterA.getCount() + 10) {
						//synchronize global counter, and then add appropriately
						synchronized(counterB) {
							counterB.addToCounter(2);
						}
						//write job to slave B
						try {
							writeToSlaveB.writeObject(jobArray.get(0));
						} catch (IOException e) {
							e.printStackTrace();
						}
						System.out.println("Master writing job to slave B: " + jobArray.get(0));

					}
					else {
						//synchronize global counter, and then add appropriately
						synchronized(counterA) {
							counterA.addToCounter(10);
						}
						try {
							//write job to slaveA
							writeToSlaveA.writeObject(jobArray.get(0));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println("Master writing job to slave A: " + jobArray.get(0));
					}

				}
				//once sent job to slave, remove the job
				jobArray.remove(0);
			}
			
			//if the job Array is empty, sleep for half a second.
			else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}

