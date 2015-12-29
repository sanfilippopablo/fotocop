package users.exceptions;

/**
 * Custom exception para cuestiones relacionadas con
 * auth.
 */
public class AuthException extends Exception {

	private static final long serialVersionUID = 1L;

	public AuthException(String message) {
		super(message);
	}
	
}
