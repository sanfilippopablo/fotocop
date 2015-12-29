package users.data;

import users.entities.User;
import users.exceptions.AuthException;

/**
 * 
 * DAO para Users.
 *
 */
public class UsersService {
	
	/**
	 * Autentica a un usuario. Es decir, dados un username y
	 * una contraseña devuelve el objeto User correspondiente si
	 * el User existe y los datos son correctos, o tira un error
	 * AuthException en caso contrario.
	 * 
	 * @param username
	 * @param password
	 * @return User
	 * @throws AuthException
	 */
	public User authenticate(String username, String password) throws AuthException {
		// TODO This is a stub. Escribir la implementación correcta
		// usando getUserByUsername y checkPassword y esa onda.

		if (username.equals("pablo") && password.equals("123")) {
			User user = new User();
			user.setUsername("pablo");
			user.setEmail("sanfilippopablo@gmail.com");
			return user;
		}
		else {
			throw new AuthException("Usuario inválido.");
		}
	}

}
