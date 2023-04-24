package finalAssignment;

import java.io.*;
import java.util.*;

public class masterReadClientThread extends Thread {

	private BufferedReader readClient;
	private ArrayList <Job> jobArray;
	private int clientID;
	
	public masterReadClientThread(BufferedReader readClient, ArrayList <Job> JobArray, int clientID) {
		this.readClient = readClient;
		this.jobArray = JobArray;
		this.clientID = clientID;
	}
	
	@Override
	public void run() {
		System.out.println("Waiting for request from client " + clientID);
		System.out.println();

		try {
			//initialize job id counter and String
			int jobId = 0;
			String jobType;
			while(true) {
			String jobFromClient = readClient.readLine();			
			if(jobFromClient != null){
					
					jobId =  Integer.parseInt(jobFromClient.substring(1)) ;
					jobType = jobFromClient.substring(0, 1);
					
					//create new job and assign job type, jobId, and clientID
					Job newJob = new Job(JobType.valueOf(jobType), jobId, clientID);
					System.out.println("Received from client: Job Type: " + newJob.getjType() + " Job ID: " + newJob.getId() );
					System.out.println();

					
					//add new job to job array
					synchronized(jobArray){
						jobArray.add(newJob);
					}
					
			}
			else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
