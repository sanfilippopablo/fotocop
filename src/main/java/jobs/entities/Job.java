package jobs.entities;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import users.entities.User;

public class Job {
	int id;
	User user;
	String status;
	Date creationDate;
	Date lastModifiedDate;
	ArrayList<JobLine> jobLines = new ArrayList<JobLine>();
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
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}
	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}
	public void setJobLines(ArrayList<JobLine> jobLines) {
		this.jobLines = jobLines;
	}	
	public ArrayList<JobLine> getJobLines(){

		return jobLines;
	}
	public void addJobLine(JobLine jl){
		this.jobLines.add(jl);
	}
	public Date getEta() {
		return eta;
	}
	public void setEta(Date eta) {
		this.eta = eta;
	}
	/**
	 * Calcula y devuelve el tiempo que se tarda en imprimir el trabajo (en milisegundos).
	 * 
	 * 
	 */ 
	public long getPrintingTime(){
		long averagePrintingTimePerFile = 60000; //milisegundos, es decir, 1 minuto
		long printingT=0;
		for (JobLine jl : jobLines) {
			printingT+= averagePrintingTimePerFile * jl.getQuantity();
		}
		return printingT;
	}
}
