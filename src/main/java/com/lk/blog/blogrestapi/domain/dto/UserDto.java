package com.lk.blog.blogrestapi.domain.dto;

import java.util.Set;

import com.lk.blog.blogrestapi.domain.entity.Role;

import lombok.Data;

@Data
public class UserDto {

	private String name;
	private String email;
	private String username;
	private Set<Role> roles;
	
}
