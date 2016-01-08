package jobs.data;

import jobs.entities.File;
import jobs.entities.Job;
import jobs.entities.JobLine;

public class PrintService {

	/**
	 * Imprime el archivo que se le pasa por parámetro.
	 * Este es el método que haría el printing posta,
	 * Pero no hace nada.
	 * 
	 * @param file
	 */
	public void printFile(File file) {
		// Print
		System.out.println("Printing " + file.getName());
	}
	
	
	/**
	 * Imprime el Job especificado.
	 * 
	 * @param job
	 */
	public void printJob(Job job) {
		for (JobLine jobLine: job.getJobLines()) {
			this.printFile(jobLine.getFile());
		}
	}
	
	
}
