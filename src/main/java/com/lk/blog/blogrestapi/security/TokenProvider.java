package com.lk.blog.blogrestapi.security;

import org.springframework.security.core.Authentication;

public interface TokenProvider {

	public String generateToken(Authentication  auth);
	public String getUserNameFromToken(String token);
	public boolean isValid(String token);
}
