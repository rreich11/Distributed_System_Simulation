package finalAssignment;

import java.io.*;
import java.net.*;
import java.util.*;

public class Master {

	public static void main(String[]args) {
		
		//hardcode port number
		args = new String[] {"30121"};
		int portNumber = Integer.parseInt(args[0]);
		
		ArrayList<Job> jobArray = new ArrayList<Job>();
		SlaveCounter counterA = new SlaveCounter(0);
		SlaveCounter counterB = new SlaveCounter(0);

		
		try(ServerSocket serverSocket = new ServerSocket(portNumber);){
			
			Socket clientSocket1 = serverSocket.accept();
			System.out.println("client one connect");
			Socket clientSocket2 = serverSocket.accept();
			System.out.println("client two connect");

			Socket slaveSocketA = serverSocket.accept();
			System.out.println("slave a connect");

			Socket slaveSocketB = serverSocket.accept();
			System.out.println("slave b connect");

			
			//created Two client sockets and individual buffered readers and writers
			PrintWriter writeToClient1 = new PrintWriter(clientSocket1.getOutputStream(),true);
			BufferedReader readFromClient1 = new BufferedReader(new InputStreamReader(clientSocket1.getInputStream()));
			PrintWriter writeToClient2 = new PrintWriter(clientSocket2.getOutputStream(),true);
			BufferedReader readFromClient2 = new BufferedReader(new InputStreamReader(clientSocket2.getInputStream()));
			
			
			//Writing and Reading job objects to and from slaves
			ObjectOutputStream writeObjectToSlaveA = new ObjectOutputStream(slaveSocketA.getOutputStream());
			ObjectOutputStream writeObjectToSlaveB = new ObjectOutputStream(slaveSocketB.getOutputStream());
			ObjectInputStream readObjectFromSlaveA = new ObjectInputStream(slaveSocketA.getInputStream());
			ObjectInputStream readObjectFromSlaveB = new ObjectInputStream(slaveSocketB.getInputStream());
			
			
			//create thread to read from client 1
			Thread readClientThread1 = new masterReadClientThread (readFromClient1, jobArray, 1);
			//create thread to read from client 2
			Thread readClientThread2 = new masterReadClientThread (readFromClient2, jobArray, 2);
			
			//create thread to write to slaves
			Thread writeToSlaves = new masterWriteToSlaveThread (writeObjectToSlaveA, writeObjectToSlaveB, jobArray, counterA, counterB);
			
			//arrays to hold completed jobs, one for each client
			ArrayList <Job> completedJobs1 = new ArrayList<Job> ();
			ArrayList <Job> completedJobs2 = new ArrayList<Job> ();
			
			//create thread to read from slaveA and slaveB
			Thread masterReadFromSlaveA = new MasterReadFromSlaveA(readObjectFromSlaveA, completedJobs1, completedJobs2, counterA, counterB);
			Thread masterReadFromSlaveB = new MasterReadFromSlaveB(readObjectFromSlaveB, completedJobs1, completedJobs2, counterA, counterB);

			
			
			//write back to clients with completed jobs
			Thread writeToClientThread1 = new masterWriteToClientThread(writeToClient1, completedJobs1);
			Thread writeToClientThread2 = new masterWriteToClientThread(writeToClient2, completedJobs2);
			
			//run threads
			readClientThread1.start();
			readClientThread2.start();
			writeToSlaves.start();
			masterReadFromSlaveA.start();
			masterReadFromSlaveB.start();
			writeToClientThread1.start();
			writeToClientThread2.start();
			
			
			readClientThread1.join();
			readClientThread2.join();
			writeToSlaves.join();
			masterReadFromSlaveA.join();
			masterReadFromSlaveB.join();
			writeToClientThread1.join();
			writeToClientThread2.join();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	
	}
}

