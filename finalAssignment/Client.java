package finalAssignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
public static void main(String[] args) throws IOException {
		
		//Hard code in IP and Port here if required
    	args = new String[] {"127.0.0.1", "30121"};
    	String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        
        try(
        	Socket clientSocket = new Socket(hostName, portNumber);
        	PrintWriter writeToMaster = new PrintWriter(clientSocket.getOutputStream(),true);
        	BufferedReader readFromMaster = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ){
        	
        	//create thread passing printWriter 
        	Thread writeToMasterThread = new clientWriteToMasterThread(writeToMaster);
        	
        	//run thread
        	writeToMasterThread.start();
        		      	
        	//read from the master when jobs are completed
        	Thread readMaster = new clientReadMasterThread(readFromMaster);
        	
        	//run thread
        	readMaster.start();
        	
        	writeToMasterThread.join();
        	readMaster.join();
        	
        	clientSocket.close();
        	
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
      } catch (InterruptedException e) {
			e.printStackTrace();
		}   
	}
}
