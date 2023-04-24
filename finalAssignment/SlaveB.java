package finalAssignment;


import java.io.*;
import java.net.*;



public class SlaveB {

	public static void main(String[] args){
		String IPAddress = "127.0.0.1";
		int portNumber = 30121;

		//socket: slave B to master
		try (	Socket slaveBSocket = new Socket(IPAddress, portNumber);
				ObjectOutputStream os = new ObjectOutputStream(slaveBSocket.getOutputStream());
				ObjectInputStream is = new ObjectInputStream(slaveBSocket.getInputStream());
				){

			//read a job from the master
			Job job = (Job) is.readObject();

			while( job  != null) { 
				//if job is type b sleep for two seconds
				if(job.getjType() == JobType.B ) {
					try {
						System.out.println("Slave B, working on Job type B");
						Thread.sleep(2000);					
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				//if job is type a, sleep for 10 seconds
				else {
					try {
						System.out.println("Slave B, working on Job type A");
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}		
				}
				System.out.println();
				System.out.println("Slave B finished working on: \n\t" + job + ". \nReturning to master ");
				System.out.println();

				//once job is complete, return to master
				os.writeObject(job);	

				//read next job from master
				job = (Job) is.readObject();
			}
		}
		catch (UnknownHostException e) {
			System.err.println("Don't know about host " + IPAddress);
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to " +
					IPAddress);
			System.err.println(e.getStackTrace());
			System.exit(1);
		} catch (ClassNotFoundException e) {
			System.err.println("Error, class not found ");
		} 


	}

}


