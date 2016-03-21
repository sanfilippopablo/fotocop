package jobs.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.exceptions.BadRequestException;
import common.servlets.LoginRequiredServlet;
import jobs.data.JobsService;
import jobs.entities.Job;
import jobs.entities.File;
import jobs.entities.JobLine;
import users.entities.User;
import users.exceptions.AuthException;
import jobs.data.FilesService;

import java.io.PrintWriter;
import java.util.Collection;
 
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;

@WebServlet("/upload")
@MultipartConfig(location = "/var/lib/openshift/12592ddfd6824bd88a45a3dd111b2ced/app-root/data")
/**
 * Agrega un archivo a un trabajo
 * 
 * 
 */

public class AddFileToJobServlet extends LoginRequiredServlet {
    private static final long serialVersionUID = 1L;
	public AddFileToJobServlet() {
		// TODO Auto-generated constructor stub
		super();
	}
	/**
	 * Por GET tira error. No se permite por GET.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new BadRequestException("Sólo por POST.");
	}
	/**
	 * Por POST ejecuta el behavior descrito arriba.
	 *   
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		//Inicializamos los Services
		FilesService fs = new FilesService();
		JobsService jobsService = new JobsService();
		//Obtenemos la data por Post 
		Integer quantity = Integer.parseInt(request.getParameter("quantity"));
		Integer abrochado = Integer.parseInt(request.getParameter("abrochado"));
		Integer anillado = Integer.parseInt(request.getParameter("anillado"));
		Integer dobleFaz = Integer.parseInt(request.getParameter("dobleFaz"));
		User user = (User) request.getAttribute("user");
		Integer jobId = Integer.parseInt(request.getParameter("id"));
		
		//Identificamos el job correspondiente
		Job job = null;
        try {
            job = jobsService.getJobById(jobId);
        } catch (SQLException e) {
            throw new ServletException();
        }
        
        //Obtenemos el nombre del archivo 
        Collection<Part> parts = request.getParts();
        Part part = parts.iterator().next();
        String filename;
        filename = getFileName(part);
        
        InputStream stream = part.getInputStream(); //AGARRARLO
        //Obtenemos el archivo
        File file = new File();
		try {
			file = fs.createFileFromInputStream(stream, filename);
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
        //Creamos la jobLine y le seteamos la metadata
        JobLine jobLine = new JobLine();
        jobLine.setFile(file);
        jobLine.setQuantity(quantity);
        jobLine.setAbrochado(abrochado.equals(1));
        jobLine.setDobleFaz(dobleFaz.equals(1));
        jobLine.setAnillado(anillado.equals(1));
        //La agregamos al Job
        try {
			jobsService.addJobLineToJob(job, jobLine);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Obtiene el nombre del archivo
	 */
    private String getFileName(Part part) {
        String partHeader = part.getHeader("content-disposition");
        for (String cd : partHeader.split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}
