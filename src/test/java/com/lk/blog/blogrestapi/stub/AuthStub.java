package com.lk.blog.blogrestapi.stub;

import com.lk.blog.blogrestapi.domain.dto.SignUpDto;
import com.lk.blog.blogrestapi.domain.dto.UserDto;
import com.lk.blog.blogrestapi.domain.entity.User;

public class AuthStub {
		
		public SignUpDto getSignUpDtoStub() {
			SignUpDto signUpDto = new SignUpDto();
			signUpDto.setName("test man IT");
			signUpDto.setEmail("testman.it@gmail.com");
			signUpDto.setUsername("testman.it");
			signUpDto.setPassword("testmanp");
			signUpDto.setRoleName("ROLE_USER");
			
			return signUpDto;
		}
		
		
		
//		public User mapSignUpDtoToUser() {
//			
//		}
	}