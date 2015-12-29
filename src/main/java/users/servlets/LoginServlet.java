package users.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		if ((Boolean) session.getAttribute("isLogged")) {
			response.sendRedirect("/");
			return;
		}
		
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
		// TODO Implementar la lógica descrita arriba.
	}

}
