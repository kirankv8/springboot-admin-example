package com.admin.application.exceptionhandler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException resEx,
			WebRequest request) {
		ExceptionResponse exception = new ExceptionResponse(new Date(), resEx.getMessage(),
				request.getDescription(false), resEx.getStatus());
		return new ResponseEntity<Object>(exception, HttpStatus.BAD_REQUEST);
	}
   
  @ExceptionHandler(EmailAlreadyExistException.class)
  public ResponseEntity<ExceptionResponse>emailAlready(EmailAlreadyExistException alreadyExistException,WebRequest request){
	ExceptionResponse response=new ExceptionResponse(new Date(), alreadyExistException.getMessage(),request.getDescription(false),alreadyExistException.getStatus());
	return new ResponseEntity<ExceptionResponse>(response,HttpStatus.BAD_REQUEST);
  }
  @ExceptionHandler(UserNameAlreadyExistException.class)
	public ResponseEntity<?> phoneNumberExResponse(UserNameAlreadyExistException userex,
			WebRequest request) {
		ExceptionResponse exception = new ExceptionResponse(new Date(), userex.getMessage(),
				request.getDescription(false), userex.getStatus());
		return new ResponseEntity<Object>(exception, HttpStatus.BAD_REQUEST);
	}
   
}
