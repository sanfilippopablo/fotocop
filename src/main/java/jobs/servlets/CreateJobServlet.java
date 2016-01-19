package jobs.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jobs.data.JobsService;
import jobs.entities.Job;
import users.entities.User;
import users.exceptions.AuthException;

/**
 * Crea un nuevo trabajo para el user en sesión y lo manda a la
 * página de ese trabajo si no tiene uno ya creado. Si resulta que
 * el user en sesión ya tiene un trabajo abierto, lo manda a ese.
 * 
 * No renderiza ningún JSP, sólo hace redirecciones.
 */
@WebServlet("/CreateJobServlet")
public class CreateJobServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateJobServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * Por GET tira error. No se permite por GET.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(400);
		response.getWriter().append("No vale por GET, sólo por POST.");
	}

	/**
	 * Por POST ejecuta el behavior descrito arriba.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Si no está logueado, mandarlo a la página de login.
		if (! (Boolean) request.getSession().getAttribute("isLogged")) {
			response.sendRedirect("/login");
			return;
		}
		
		User user = (User) request.getSession().getAttribute("user");
		JobsService jobsService = new JobsService();
		ArrayList<Job> jobs = new ArrayList<Job>();
		try {
			jobs = jobsService.getPendingJobsForUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		
		// Si ya existe un Job abierto para ese user, mostrarle ese
		for (Job job: jobs) {
			if (job.getStatus() == "Abierto") {
				response.sendRedirect("/job?id=" + Integer.toString(job.getId()));
				return;
			}
		}
		
		// Si no tiene ningún Job abierto, crearle uno y mostrárselo
		Job job;
		try {
			job = jobsService.createJobForUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		response.sendRedirect("/job?id=" + Integer.toString(job.getId()));
	}

}