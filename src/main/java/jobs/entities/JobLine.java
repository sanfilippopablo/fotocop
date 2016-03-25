package jobs.entities;

/**
 * 
 * Representa una Job line / línea de trabajo, correspondiente a un archivo e información sobre qué hay que hacerle.
 *
 */

public class JobLine {
	int quantity;
	File file;
	boolean anillado;
	boolean abrochado;
	boolean dobleFaz;
	
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	Job job;
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public boolean isAnillado() {
		return anillado;
	}
	public void setAnillado(boolean anillado) {
		this.anillado = anillado;
	}
	public boolean isAbrochado() {
		return abrochado;
	}
	public void setAbrochado(boolean abrochado) {
		this.abrochado = abrochado;
	}
	public boolean isDobleFaz() {
		return dobleFaz;
	}
	public void setDobleFaz(boolean dobleFaz) {
		this.dobleFaz = dobleFaz;
	}
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}


}
