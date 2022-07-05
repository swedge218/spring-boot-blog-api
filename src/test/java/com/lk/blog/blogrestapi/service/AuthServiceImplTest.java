package com.lk.blog.blogrestapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.lk.blog.blogrestapi.domain.entity.User;
import com.lk.blog.blogrestapi.domain.repository.PostRepository;
import com.lk.blog.blogrestapi.domain.repository.UserRepository;
import com.lk.blog.blogrestapi.exception.BadRequestException;
import com.lk.blog.blogrestapi.service.impl.AuthServiceImpl;
import com.lk.blog.blogrestapi.stub.AuthStub;

@ExtendWith(SpringExtension.class)
public class AuthServiceImplTest {

	@Mock
	UserRepository userRepository;
	
	@InjectMocks
	AuthServiceImpl authService;
	
	@Test
	void whenExistingUserNameIsEnteredOnSignUp_thenThrowBadRequestException() {
		AuthStub authStub = new AuthStub();
		when(userRepository.existsByUsername(anyString())).thenReturn(true);
		
		BadRequestException ex = assertThrows(BadRequestException.class, () -> {
			authService.signUpUser(authStub.getSignUpDtoStub());
		});

		assertEquals(400, ex.getStatus().value());
		assertTrue(ex.getMessage().toUpperCase().contains("already exists".toUpperCase()));
	}
	
	@Test
	void whenExistingEmailIsEnteredOnSignUp_thenThrowBadRequestException() {
		AuthStub authStub = new AuthStub();
		when(userRepository.existsByEmail(anyString())).thenReturn(true);
		
		BadRequestException ex = assertThrows(BadRequestException.class, () -> {
			authService.signUpUser(authStub.getSignUpDtoStub());
		});

		assertEquals(400, ex.getStatus().value());
		assertTrue(ex.getMessage().toUpperCase().contains("already exists".toUpperCase()));
	}
	
}
