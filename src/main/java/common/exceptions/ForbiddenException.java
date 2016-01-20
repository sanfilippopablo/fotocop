package common.exceptions;

import javax.servlet.ServletException;

public class ForbiddenException extends ServletException {
	private static final long serialVersionUID = 1L;

	public ForbiddenException(String message) {
		super(message);
	}
}
