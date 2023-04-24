package finalAssignment;

import java.io.Serializable;

public class Job implements Serializable{
	
	
	
	private static final long serialVersionUID = 5950169519310163575L;
	private JobType jType;
	private int id;
	private int clientID;
	
	
	public Job(JobType jType, int id, int clientID) {
		this.jType = jType;
		this.id = id;
		this.clientID = clientID;
	}

	
	public JobType getjType() {
		return jType;
	}

	public void setjType(JobType jType) {
		this.jType = jType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	
	public String toString() {
		return "Job type: " + this.jType + " Job ID: " + this.id + " Client ID: " + this.clientID;
 	}
	
	
	
	
		
	
	
}