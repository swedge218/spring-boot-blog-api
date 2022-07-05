package com.lk.blog.blogrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;
import net.bytebuddy.implementation.bytecode.constant.IntegerConstant;

@Data
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

	private String resourceName;
	private String message;
	private HttpStatus status;
	
	public BadRequestException(String message) {
		super(message, null, false, false);
		this.message = message;
		this.status = HttpStatus.BAD_REQUEST;
	}
	
	public BadRequestException(String resourceName, String message) {
		super(String.format("%s => %s", resourceName, message), null, false, false);
		this.resourceName = resourceName;
		this.message = message;
		this.status = HttpStatus.BAD_REQUEST;
	}

	
}
