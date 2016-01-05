package users.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.ValidationManager;
import users.data.UsersService;
import users.entities.User;
import users.exceptions.AuthException;

/**
 * Registration servlet se encarga de la creación de un nuevo usuario.
 * Por GET, tan sólo sirve registration.jsp. Por POST, valida los datos
 * recibidos. Si son válidos, entonces crea el nuevo usuario en la DB.
 */
public class RegistrationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public RegistrationServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/registration.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");
		String email = request.getParameter("email");
		
		ValidationManager validationManager = new ValidationManager();
		
		if ( username.isEmpty() ) {
			validationManager.addError("username", "isEmpty");
		}
		
		if ( password.isEmpty() ) {
			validationManager.addError("password", "isEmpty");
		}
		
		if ( password2.isEmpty() ) {
			validationManager.addError("password2", "isEmpty");
		}
		
		if (password != password2) {
			validationManager.addCustomError("password2", "Las contraseñas no coinciden.");
		}
		
		if ( email.isEmpty() ) {
			validationManager.addError("email", "isEmpty");
		}

		if ( validationManager.isValid() ) {
			
			// Create User
			User user = new User();
			user.setUsername(username);
			user.setEmail(email);
			user.setPassword(password);
			
			UsersService usersService = new UsersService();
			
			try {
				usersService.createUser(user);
				
				// Loguear al user
				request.getSession().setAttribute("isLogged", true);
				request.getSession().setAttribute("user", user);
				
				// Redirigirlo a /
				response.sendRedirect("/");
				return;
			} catch (AuthException e) {
				validationManager.addCustomError("username", "Ya existe un usuario con ese username.");
				
			} catch (SQLException e) {
				response.setStatus(500);
				request.getRequestDispatcher("/500.html").forward(request, response);
				return;
			}
			
		}
		
		request.setAttribute("validationManager", validationManager);
		request.getRequestDispatcher("/registration.jsp").forward(request, response);
		
	}

}
