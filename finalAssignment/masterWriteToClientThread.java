package finalAssignment;

import java.io.*;
import java.util.*;


public class masterWriteToClientThread extends Thread {

	private PrintWriter writeToClient;
	private ArrayList<Job> completedJobs;
	
	public masterWriteToClientThread(PrintWriter writeToClient, ArrayList<Job>job) {
		this.writeToClient = writeToClient;
		this.completedJobs = job;
		
	}
	
	@Override
	public void run() {
		while(true) {

			if(!completedJobs.isEmpty()) {

				System.out.println("Master returning to client " + completedJobs.get(0).getClientID() + ": " + completedJobs.get(0) );
				System.out.println();

				//send message to client
				writeToClient.println("Job Type: " + completedJobs.get(0).getjType()+ " Job ID: " + completedJobs.get(0).getId()+" completed.");

				//remove first job from list
				synchronized(completedJobs) {
					completedJobs.remove(0);
				}
			}
			//if the completed jobs Array is empty, sleep for half a second.
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
