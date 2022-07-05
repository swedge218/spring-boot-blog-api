package com.lk.blog.blogrestapi.service.abstracts;

import com.lk.blog.blogrestapi.domain.dto.JwtAuthResponse;
import com.lk.blog.blogrestapi.domain.dto.LoginDto;
import com.lk.blog.blogrestapi.domain.dto.SignUpDto;
import com.lk.blog.blogrestapi.domain.dto.UserDto;

public interface AuthService {

	public JwtAuthResponse authenticateUser(LoginDto loginDto);
	public UserDto signUpUser(SignUpDto signUpDto);
}
