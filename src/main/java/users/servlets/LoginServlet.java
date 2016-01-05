package users.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import common.ValidationManager;
import users.data.UsersService;
import users.entities.User;
import users.exceptions.AuthException;

/**
 * Se encarga de hacer el login de usuarios. Por GET, sólamente sirve
 * la página de login. Por POST, hace la validación y el logueo.
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

	/**
	 * Por GET, este servlet sólo sirve la página web de login.
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Crear la página /login.jsp

		// Si ya está logueado, redirigir al home
		HttpSession session = request.getSession();
		if ((Boolean) session.getAttribute("isLogged") != null && (Boolean) session.getAttribute("isLogged")) {
			response.sendRedirect("/");
			return;
		}

		session.setAttribute("isLogged", false);
		// Servir login page
		request.getRequestDispatcher("/login.jsp").forward(request, response);
		
	}

	/**
	 * Por POST, se reciben dos datos: username y password.
	 * doPost se encarga de obtener un usuario a través de UsersService,
	 * loguearlo en el request, y redirigir al home. Si hay error,
	 * vuelve a mostrar la página de login con el informe de errores.
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		// Single field validations

		ValidationManager validationManager = new ValidationManager();

		if (username == null || username.isEmpty()) {
			validationManager.addError("username", "isEmpty");
		}

		if (password == null || password.isEmpty()) {
			validationManager.addError("password", "isEmpty");
		}

		if (validationManager.isValid()) {

			UsersService usersService = new UsersService();

			User user = null;
			try {
				user = usersService.authenticate(username, password);
			}
			catch (AuthException e) {
				// Authentication failed
				validationManager.addCustomError("misc", e.getMessage());
				request.setAttribute("validationManager", validationManager);
				request.getRequestDispatcher("/login.jsp").forward(request, response);
				return;
			}
			catch (SQLException e) {
				response.setStatus(500);
				request.getRequestDispatcher("/500.html").forward(request, response);
			}
			
			// Actual login
			HttpSession session = request.getSession();
			session.setAttribute("isLogged", true);
			session.setAttribute("user", user);
			response.sendRedirect("/");

		} else {
			request.setAttribute("validationManager", validationManager);
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

}
