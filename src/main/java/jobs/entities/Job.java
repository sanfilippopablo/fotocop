package jobs.entities;
import java.util.ArrayList;
import java.util.Date;

import users.entities.User;

public class Job {
	int id;
	User user;
	String status;
	Date creation;
	Date lastModified;
	ArrayList<JobLine> jobLines;
	Date eta;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public void setJobLines(ArrayList<JobLine> jobLines) {
		this.jobLines = jobLines;
	}	
	
	public ArrayList<JobLine> getJobLines(){

		return jobLines;
	}

	public Date getEta() {
		return eta;
	}

	public void setEta(Date eta) {
		this.eta = eta;
	}
	
}
