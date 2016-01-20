package jobs.servlets;


import jobs.entities.Job;
import users.entities.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.servlets.LoginRequiredServlet;
import jobs.data.JobsService;

/**
 * Dashboard del usuario. Es la página en donde se muestran los
 * trabajos pendientes del usuario.
 * 
 * Renderiza el JSP user_dashboard.jsp.
 * 
 * Al JSP se le pasan los siguientes atributos:
 * 
 * - jobs: La lista de los trabajos pendientes para el usuario.
 */
public class UserDashboardServlet extends LoginRequiredServlet {
	private static final long serialVersionUID = 1L;
       
    public UserDashboardServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		JobsService jobsService = new JobsService();
		
		ArrayList<Job> jobs = new ArrayList<Job>();
		try {
			jobs = jobsService.getPendingJobsForUser((User) request.getAttribute("user"));
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ServletException();
		}
		
		request.setAttribute("jobs", jobs);
		request.getRequestDispatcher("/user_dashboard.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
