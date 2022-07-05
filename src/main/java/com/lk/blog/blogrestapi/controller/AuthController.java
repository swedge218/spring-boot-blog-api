package com.lk.blog.blogrestapi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lk.blog.blogrestapi.domain.dto.JwtAuthResponse;
import com.lk.blog.blogrestapi.domain.dto.LoginDto;
import com.lk.blog.blogrestapi.domain.dto.SignUpDto;
import com.lk.blog.blogrestapi.domain.dto.UserDto;
import com.lk.blog.blogrestapi.service.abstracts.AuthService;

@RestController
@RequestMapping("/api")
public class AuthController {

	AuthService authService;
	
	
	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}


	@PostMapping("/auth/v1/signin")
	public ResponseEntity<JwtAuthResponse> signIn(
			@RequestBody LoginDto loginDto) {

		return ResponseEntity
				.status(HttpStatus.OK)
				.body(authService.authenticateUser(loginDto));
	}
	
	@PostMapping("/auth/v1/signup")
	public ResponseEntity<UserDto> signUp(@RequestBody SignUpDto signUpDto) {
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(authService.signUpUser(signUpDto));
	}
	
	@GetMapping("/auth/v1/sign-out-success")
	public ResponseEntity<String> signOutSuccess(){
		return ResponseEntity.status(HttpStatus.OK).body("Signed out successfully");
	}
	
	// generation and usage of refresh token
	// new login by a user must invalidate any existing tokens owned by the same user
	// log out by a user must invalidate any existing tokens owned by the same user
	
}
