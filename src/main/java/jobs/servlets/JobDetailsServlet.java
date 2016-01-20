package jobs.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.exceptions.ForbiddenException;
import common.exceptions.NotFoundException;
import common.servlets.LoginRequiredServlet;
import jobs.data.JobsService;
import jobs.entities.Job;
import users.entities.User;

/**
 * Página de detalles de Job. Antes de mostrarla, chequea que
 * el Job que se está pidiendo pertenezca al user. Alto agujero
 * de seguridad sino (coff coff sysacad coff coff).
 * 
 * Renderiza el JSP jobDetails.jsp. Este JSP recibe los siguientes
 * parámetros:
 * 
 * - job: El Job del que se deben mostrar los detalles.
 */
public class JobDetailsServlet extends LoginRequiredServlet {
	private static final long serialVersionUID = 1L;

    public JobDetailsServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JobsService jobsService = new JobsService();
		
		Integer jobId = Integer.parseInt(request.getParameter("id"));
		
		Job job = null;
		try {
			job = jobsService.getJobById(jobId);
		} catch (SQLException e) {
			throw new ServletException();
		}
		
		if (!(job.getUser().getId() == ((User) request.getAttribute("user")).getId())) {
			throw new ForbiddenException("No tenés permiso para ver este trabajo.");
		}
		
		request.setAttribute("job", job);
		request.getRequestDispatcher("/jobDetails.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
