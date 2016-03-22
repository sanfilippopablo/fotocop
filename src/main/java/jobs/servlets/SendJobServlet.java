package jobs.servlets;

import java.io.IOException;
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
import users.entities.User;
import users.exceptions.AuthException;

/**
 * Envía un trabajo a imprimir
 * 
 * No renderiza ningún JSP, sólo hace redirecciones.
 */

public class SendJobServlet extends LoginRequiredServlet {

	/**
     * @see HttpServlet#HttpServlet()
     */
    public SendJobServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
	/**
	 * Por GET tira error. No se permite por GET.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		throw new BadRequestException("Sólo por POST.");
	}
	/**
	 * Por POST ejecuta el behavior descrito arriba.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		User user = (User) request.getAttribute("user");
		Integer jobId = Integer.parseInt(request.getParameter("id"));
		JobsService jobsService = new JobsService();
		
		Job job = null;
        try {
            job = jobsService.getJobById(jobId);
        } catch (SQLException e) {
            throw new ServletException();
        }
        //Primero, se valida si el job es de ese usuario
        if (job.getUser().getId() == user.getId()){
        	//Luego, entonces, se submittea
        	try {
    			jobsService.submitjob(job);
    		} catch (SQLException e) {
    			e.printStackTrace();
    			throw new ServletException();
    		}
    		response.sendRedirect("/");
        }
	}
}
