package com.lk.blog.blogrestapi.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.lk.blog.blogrestapi.domain.constant.AppConstants;
import com.lk.blog.blogrestapi.exception.BlogApiException;

@Component("java-jwt")
//@Qualifier("java-jwt")
public class JavaJwtTokenProvider implements TokenProvider {

	@Value("${blogapp.jwt.secret.key}")
	private String jwtSecret;
	
	@Value("${blogapp.jwt.expiration}")
	private int jwtExpirationInMilliseconds;
	
	@Value("${blogapp.jwt.issuer}")
	private String jwtIssuer;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public String generateToken(Authentication auth) {
		String username = auth.getName();
		Date now = new Date();
		Date expiration = new Date(now.getTime() + jwtExpirationInMilliseconds);
		
		System.out.println(String.format("Secret: %s, Millis: %d", 
				jwtSecret, jwtExpirationInMilliseconds));
		
		try {
		    Algorithm algorithm = Algorithm.HMAC512(jwtSecret);
		    return JWT.create()
		        .withIssuer(jwtIssuer)
		        .withIssuedAt(now)
		        .withSubject(username)
		        .withExpiresAt(expiration)
		        .sign(algorithm);
		} catch (JWTCreationException exception){
		    //Invalid Signing configuration / Couldn't convert Claims.
			throw new BlogApiException(
					"Invalid Signing configuration / Couldn't convert Claims");
		}
	}
	
	public void generateRefreshToken() {
		/**
			To receive a new access token using the refresh_token grant type, 
			the user no longer needs to enter their 
			credentials, but only the client id, secret and of course the refresh token.
			
			The goal of using two types of tokens is to enhance user security. 
			Typically the access token has a shorter validity period so that if an attacker obtains 
			the access token, they have a limited time in which to use it. 
			On the other hand, if the refresh token is compromised, this is useless as the client id 
			and secret are also needed.
		*/
		
	}

	@Override
	public String getUserNameFromToken(String token) {
		
		try {
			if(!isValid(token)) throw new JWTVerificationException("Invalid token");
				
			DecodedJWT jwt = JWT.decode(token);
			return jwt.getSubject();
			
		} catch (JWTDecodeException exception){
			throw new BlogApiException("Invalid token / could not retrieve subject");
		}
	}

	@Override
	public boolean isValid(String token) {
		try {
		    Algorithm algorithm = Algorithm.HMAC256(jwtSecret); //use more secure key
		    JWTVerifier verifier = JWT.require(algorithm)
		        .withIssuer(jwtIssuer)
		        .build(); //Reusable verifier instance
		    DecodedJWT jwt = verifier.verify(token);

		    return true;
		    
		} catch (JWTVerificationException exception){
			throw new BlogApiException("Invalid signature/claims");
		}
	}

	
}
