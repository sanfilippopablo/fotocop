package common.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import users.data.UsersService;
import users.entities.User;
import users.exceptions.AuthException;

/**
 * Servlet abstracto. Provee métodos de utilidad para los servlets que lo hereden.
 * 
 */
public abstract class BaseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public BaseServlet() {
        super();
    }

	/**
	 * Chequea si un usuario está logueado. Además, modifica el request recibido,
	 * agregándole un attribute user con el user, si está logueado.
	 */
	protected boolean isLoggedIn(HttpServletRequest request) throws ServletException, IOException {

		HttpSession session = request.getSession();

		Integer userId = null;
		userId = (Integer) request.getSession().getAttribute("userId");
		
		if (userId == null) {
			return false;
		}
		
		else {
			UsersService usersService = new UsersService();
			
			User user = null;
			try {
				user = usersService.getUserById(userId);
			} catch (AuthException | SQLException e) {
				e.printStackTrace();
				throw new ServletException();
			}
			request.setAttribute("user", user);
			return true;
		}

	}
	
	protected void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		response.sendRedirect("/login?next=" + request.getRequestURI());
	}

}
