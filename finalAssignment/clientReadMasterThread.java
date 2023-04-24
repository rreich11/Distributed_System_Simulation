package finalAssignment;

import java.io.*;

public class clientReadMasterThread extends Thread {
	
	private BufferedReader readMaster;
	
	public clientReadMasterThread(BufferedReader readMaster) {
		this.readMaster = readMaster;
	}
	
	@Override
	public void run() {
		
		try {
			
			//initialize
			String masterResponse;
			while(true) {
			//read input from master 
			if((masterResponse = readMaster.readLine())!=null) {
				
				//print out message
				System.out.println(masterResponse);
				System.out.println();

			}
			else {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
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

