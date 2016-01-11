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
			user.setId(resultSet.getInt("id"));
			user.setUsername(username);
			user.setEmail(resultSet.getString("email"));
			user.setHashedPassword(resultSet.getString("password"));
			return user;
		}
	}
	/**
	 * Devuelve un objeto User tomando como parámetro
	 * el id.
	 * 
	 * @param id
	 * @return User
	 * @throws AuthException
	 * @throws SQLException 
	 */
	public User getUserById(int id) throws AuthException, SQLException {
		
		Connection connection = DBConnection.getConnection();
		String sql = "SELECT * FROM users WHERE id = ?;";
		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setInt(1, id);
		ResultSet resultSet = statement.executeQuery();
		
		if (!resultSet.next()) {
			throw new AuthException("Usuario inexistente.");
		}
		else {
			User user = new User();
			user.setId(resultSet.getInt("id"));
			user.setUsername(resultSet.getString("username"));
			user.setEmail(resultSet.getString("email"));
			user.setHashedPassword(resultSet.getString("password"));
			return user;
		}
	}
	/**
	 * Guarda un usuario dado en la DB, y lo devuelve. El user devuelto,
	 * vuelve con el ID generado por la DB. Si ya existe un user con el
	 * mismo username o email, tira AuthException.
	 * 
	 * @param user
	 * 
	 * @throws AuthException
	 * @throws SQLException 
	 */
	
	public User createUser(User user) throws AuthException, SQLException {
		
		Connection connection = DBConnection.getConnection();
		String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?);";
		PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

		statement.setString(1, user.getUsername());
		statement.setString(2, user.getHashedPassword());
		statement.setString(3, user.getEmail());
		
		try {
			statement.executeUpdate();
			ResultSet resultSet = statement.getGeneratedKeys();
			resultSet.next();
			user.setId(resultSet.getInt(1));
		} catch (SQLException e) {
			if (e.getErrorCode() == 1062) {
				throw new AuthException(e.getMessage());
			}
			else {
				throw e;
			}
		}

		return user;
	}

}
