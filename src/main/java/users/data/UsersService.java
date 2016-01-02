package users.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import common.DBConnection;
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
	 * @throws SQLException 
	 */
	public User authenticate(String username, String password) throws AuthException, SQLException {
		
		User user = getUserByUsername(username);
		
		if (user.checkPassword(password)) {
			return user;
		}
		else {
			throw new AuthException("Contraseña incorrecta.");
		}
	}
	
	/**
	 * Devuelve un objeto User tomando como parámetro
	 * el username.
	 * 
	 * @param username
	 * @return User
	 * @throws AuthException
	 * @throws SQLException 
	 */
	public User getUserByUsername(String username) throws AuthException, SQLException {
		
		Connection connection = DBConnection.getConnection();
		String sql = "SELECT * FROM users WHERE username = ?;";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setString(1, username);
		ResultSet resultSet = statement.executeQuery();
		
		if (!resultSet.next()) {
			throw new AuthException("Usuario inexistente.");
		}
		else {
			User user = new User();
			user.setUsername(username);
			user.setEmail(resultSet.getString("email"));
			user.setHashedPassword(resultSet.getString("password"));
			return user;
		}
	}

}
