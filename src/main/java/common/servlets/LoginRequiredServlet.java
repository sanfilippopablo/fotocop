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
 * Servlet abstracto. Se fija si el usuario está logueado. Si no lo está,
 * redirecciona a la página de login, enviándole la URI de este servlet
 * en el parámetro next.
 * 
 * Todos los servlets que hereden de este, se aseguran que el usuario está
 * logueado sin mayor chequeo. Además reciben el User como attribute del 
 * request, en vez de obtenerlo de la sesión.
 */
public abstract class LoginRequiredServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LoginRequiredServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		// Si no está logueado, mandarlo a la página de login.
		if ( ! (Boolean) session.getAttribute("isLogged") ) {
			session.setAttribute("isLogged", false);	
			response.sendRedirect("/login?next=" + request.getRequestURI());
		}
		
		// Si está logueado, setear el user
		else {
			UsersService usersService = new UsersService();
			Integer userId = (Integer) session.getAttribute("userId");
			
			User user = null;
			try {
				user = usersService.getUserById(userId);
			} catch (AuthException | SQLException e) {
				e.printStackTrace();
				throw new ServletException();
			}
			request.setAttribute("user", user);
		}
		
		super.doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
