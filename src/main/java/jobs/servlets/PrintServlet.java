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
import common.servlets.BaseServlet;
import jobs.data.JobsService;
import jobs.data.PrintService;
import jobs.entities.Job;
import users.entities.User;
import users.exceptions.AuthException;

/**
 * 
 * 
 * No renderiza ningún JSP, sólo hace redirecciones.
 */
public class PrintServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PrintServlet() {
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
		
		if (isLoggedIn(request)) {
		
			Integer jobId = Integer.parseInt(request.getParameter("id"));
			User user = (User) request.getAttribute("user");
			
			JobsService jobsService = new JobsService();
			PrintService printService = new PrintService();
			
			Job job = null;
			try {
				job = jobsService.getJobById(jobId);
			} catch (SQLException e) {
				throw new ServletException();
			}
			
			printService.printJob(job);
			job.setStatus("Printed");
			
		}
		
		else {
			redirectToLogin(request, response);
		}
	}

}
