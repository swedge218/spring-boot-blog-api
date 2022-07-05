package com.lk.blog.blogrestapi.domain.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class SignUpDto {

	@NotEmpty
	private String name;
	
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	private String username;

	@NotEmpty
	private String password;
	
	@NotEmpty
	private String roleName;
}
