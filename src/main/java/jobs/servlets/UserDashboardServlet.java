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

import common.servlets.LoginRequiredServlet;
import jobs.data.JobsService;

/**
 * Dashboard del usuario. Es la página en donde se muestran los
 * trabajos pendientes del usuario.
 * 
 * Renderiza el JSP jobs.jsp.
 * 
 * Al JSP se le pasan los siguientes atributos:
 * 
 * - jobs: La lista de los trabajos pendientes para el usuario.
 */
public class UserDashboardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserDashboardServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Login required
		Integer userId = null;
		userId = (Integer) request.getSession().getAttribute("userId");
		
		if (userId != null) {
			// Logged in. Go on.
			UsersService usersService = new UsersService();
			User user = null;
			try {
				user = usersService.getUserById(userId);
			} catch (AuthException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			JobsService jobsService = new JobsService();
			
			ArrayList<Job> jobs = new ArrayList<Job>();
			try {
				jobs = jobsService.getPendingJobsForUser(user);
			} catch (SQLException e) {
				e.printStackTrace();
				throw new ServletException();
			}
			
			request.setAttribute("jobs", jobs);
			request.getRequestDispatcher("/jobs.jsp").forward(request, response);
		}
		else {
			// Not logged in. You shall not pass.
			response.sendRedirect("/login?next=" + request.getRequestURI());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
