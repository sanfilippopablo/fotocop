package jobs.servlets;


import jobs.entities.Job;
import users.data.UsersService;
import users.entities.User;
import users.exceptions.AuthException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import common.servlets.BaseServlet;
import jobs.data.JobsService;
/**
 * Dashboard del administrador. Es la página en donde se muestran los
 * trabajos pendientes de impresión.
 * 
 * Renderiza el JSP adminDashboard.jsp.
 * 
 * Al JSP se le pasan los siguientes atributos:
 * 
 * - jobs: La lista de los trabajos pendientes de impresión.
 */
public class AdminDashboardServlet extends BaseServlet{
	private static final long serialVersionUID = 1L;

	public AdminDashboardServlet() {
		super();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if (isLoggedIn(request)) {
			User user = (User) request.getAttribute("user");
			if (user.isAdmin()){
				JobsService jobsService = new JobsService();
				ArrayList<Job> jobs = new ArrayList<Job>();
				try {
					jobs = jobsService.getPendingJobs();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new ServletException();
				} catch (AuthException e) {
					e.printStackTrace();
				}
				request.setAttribute("jobs", jobs);
				request.getRequestDispatcher("/adminDashboard.jsp").forward(request, response);	
			}

		}
		else {
			// Not logged in. You shall not pass.
			redirectToLogin(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
