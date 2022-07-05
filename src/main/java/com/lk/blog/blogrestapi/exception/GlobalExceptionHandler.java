package com.lk.blog.blogrestapi.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
//400, 401, 403, 404, 500
	
	// Error Code 500
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ExceptionResponse> handleGlobalException(
			Exception exception, WebRequest request) {
		
		return exceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception, request);
	}
	
	// Error Code 400 
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ExceptionResponse> handleBadRequestException(
			BadRequestException exception, WebRequest request) {
		
		return exceptionResponse(HttpStatus.BAD_REQUEST, exception, request);
	}
	
	// Error Code 401
	@ExceptionHandler(UnauthorizedRequestException.class)
	public ResponseEntity<ExceptionResponse> handleUnauthorizedRequestException(
			UnauthorizedRequestException exception, WebRequest request) {
			
		return exceptionResponse(HttpStatus.UNAUTHORIZED, exception, request);
	}
	
	// Error Code 404
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(
			ResourceNotFoundException exception, WebRequest request) {
		
		return exceptionResponse(HttpStatus.NOT_FOUND, exception, request);
	}
	
	
	public ResponseEntity<ExceptionResponse> exceptionResponse(
			HttpStatus status, Exception exception, WebRequest request) {
		
		return ResponseEntity.status(status)
				.body(new ExceptionResponse(
						new Date(), 
						exception.getMessage(), 
						request.getDescription(false)));
	}
	
	/**
	 * Used for handlng validation errors when there are multiple fields with errors 
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
				HttpHeaders headers, HttpStatus status, WebRequest request) {
			
		Map<String, String> errors = new HashMap<String, String>();
		ex.getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError)error).getField();
			String message = ((FieldError)error).getDefaultMessage();
			errors.put(fieldName, message);
		});
		
		errors.put("path", request.getDescription(false));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
		
	}
	
}
