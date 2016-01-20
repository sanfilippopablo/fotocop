package common.exceptions;

import javax.servlet.ServletException;

public class BadRequestException extends ServletException {
	private static final long serialVersionUID = 1L;

	public BadRequestException(String message) {
		super(message);
	}
}
