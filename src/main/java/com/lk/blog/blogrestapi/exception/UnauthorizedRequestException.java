package com.lk.blog.blogrestapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedRequestException extends RuntimeException {

	
	public UnauthorizedRequestException() {
		super(String.format("Unauthorized Resource"), null, false, false);
	}

}
