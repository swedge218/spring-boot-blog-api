package com.lk.blog.blogrestapi.domain.dto;

public class JwtAuthResponse extends UserDto {

	private String accessToken;
	private String tokenType = "Bearer";
	
	
	
	public JwtAuthResponse() {
		super();
	}


	public JwtAuthResponse(String accessToken) {
		super();
		this.accessToken = accessToken;
	}


	public String getAccessToken() {
		return accessToken;
	}


	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}


	public String getTokenType() {
		return tokenType;
	}


	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	
}
