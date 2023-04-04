package com.admin.application.exceptionhandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT)
public class EmailAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final int status;

	public EmailAlreadyExistException(String message, int status) {
		super(message);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

}
