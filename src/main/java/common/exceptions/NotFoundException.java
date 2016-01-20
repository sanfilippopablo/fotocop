package common.exceptions;

import javax.servlet.ServletException;

public class NotFoundException extends ServletException {
	private static final long serialVersionUID = 1L;

	public NotFoundException(String message) {
		super(message);
	}
}
