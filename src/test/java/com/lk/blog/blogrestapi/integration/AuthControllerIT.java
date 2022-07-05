package com.lk.blog.blogrestapi.integration;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.header.Header;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.google.gson.Gson;
import com.lk.blog.blogrestapi.BlogRestApiApplication;
import com.lk.blog.blogrestapi.domain.dto.SignUpDto;
import com.lk.blog.blogrestapi.domain.dto.UserDto;
import com.lk.blog.blogrestapi.domain.entity.Role;
import com.lk.blog.blogrestapi.gateway.HeaderFactory;
import com.lk.blog.blogrestapi.gateway.TestGateway;
import com.lk.blog.blogrestapi.stub.AuthStub;
import com.lk.blog.blogrestapi.stub.TestConstants;
import com.lk.blog.blogrestapi.stub.UserStub;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=BlogRestApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@TestInstance(Lifecycle.PER_CLASS)
public class AuthControllerIT {

	@LocalServerPort
	private int port;
		
	@Test
	void userCanSignUpSuccessfully() {
		String path = "/api/auth/signup";
		HttpHeaders header = new HeaderFactory().buildPostHeaders();
		UserDto userDto = new UserStub().getUserDto();
		SignUpDto signUpDto = new AuthStub().getSignUpDtoStub();
		Role role = new Role(1L, "ROLE_USER");
		String signUpDtoJson = new Gson().toJson(signUpDto);
		
		ResponseEntity<String> response = new TestGateway(port).callPostEndpoint(path, signUpDtoJson, header);
		UserDto responseDto = new Gson().fromJson(response.getBody(), UserDto.class);
		
		System.out.println("RESP: " + response.getBody());
		System.out.println("SET STRING: " + responseDto.getRoles().toString());
		
		assertNotEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertEquals(userDto.getEmail(), responseDto.getEmail());
		assertEquals(userDto.getUsername(), responseDto.getUsername());
		assertEquals(userDto.getName(), responseDto.getName());
//		assertTrue(responseDto.getRoles().contains(role));
		
	}
}
