package users.entities;

import org.mindrot.jbcrypt.BCrypt;

/**
 * 
 * Representa un User.
 *
 */
public class User {
	String username;
	String email;
	String hashedPassword;
	
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	/**
	 * 
	 * Setea el password del user, hasheando el password
	 * ingresado y guardándolo en el campo hashedPassword.
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		String salt = BCrypt.gensalt(12);
		hashedPassword = BCrypt.hashpw(password, salt);
	}
	
	public String getHashedPassword(){
		return hashedPassword;
	}
	/**
	 * Setea el valor del campo hashedPassword al valor
	 * del parámetro. Es solo para inicialización.
	 * Cuando se quiera setear una contraseña utilizar
	 * setPassword.
	 * 
	 * @param hashedPassword
	 */
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	
	
	/**
	 * Checkea el password recibido por parámetro
	 * contra el password guardado en hashedPassword.
	 * 
	 * @param password
	 * @return Resultado del check
	 */
	public boolean checkPassword(String password) {
		return BCrypt.checkpw(password, hashedPassword);
	}

}
