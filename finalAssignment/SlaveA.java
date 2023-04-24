package finalAssignment;


import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;


public class SlaveA {

	public static void main(String[] args){
		String IPAddress = "127.0.0.1";
		int portNumber = 30121;
		
		//socket: slave A to master
		try (
	    		Socket slaveASocket = new Socket(IPAddress, portNumber);
		        ObjectOutputStream os = new ObjectOutputStream(slaveASocket.getOutputStream());
				ObjectInputStream is = new ObjectInputStream(slaveASocket.getInputStream());
	    		 ){
			
			//read a job from the master
			Job job = (Job) is.readObject();
			
			while( job != null) { 
				
				//if its job type a, sleep for 2 seconds
				if(job.getjType() == JobType.A ) {
					try {
						System.out.println("Slave A, Working on job type A");
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				//if its type B, sleep for 10 seconds
				else {
					try {
						System.out.println("Slave A, working on job type B");
						Thread.sleep(10000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}			
				}
				
				System.out.println();
				System.out.println("Slave A finished working on: \n\t" + job + ". \nReturning to master ");
				System.out.println();

				
				//once job complete, return to master
				os.writeObject(job);
				
				//read the next job
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

