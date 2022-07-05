package com.lk.blog.blogrestapi.stub;

import com.lk.blog.blogrestapi.domain.dto.UserDto;

public class UserStub {
	public UserDto getUserDto() {
		UserDto userDto = new UserDto();
		userDto.setName("test man IT");
		userDto.setEmail("testman.it@gmail.com");
		userDto.setUsername("testman.it");
		userDto.setRoles(null);

		return userDto;
	}
}

