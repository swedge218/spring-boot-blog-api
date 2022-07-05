package com.lk.blog.blogrestapi.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.lk.blog.blogrestapi.exception.BadRequestException;
import com.lk.blog.blogrestapi.exception.BlogApiException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component("jjwt")
public class JwtTokenProvider implements TokenProvider {

	@Value("${blogapp.jwt.secret.key}")
	private String jwtSecret;
	
	@Value("${blogapp.jwt.expiration}")
	private int jwtExpirationInMs;
	
	public String generateToken(Authentication  auth) {
		String username = auth.getName();
		Date now = new Date();
		Date expiration = new Date(now.getTime() + jwtExpirationInMs);
		
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(now)
				.setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public String getUserNameFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(jwtSecret)
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public boolean isValid(String token) {
		try{
			
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
            
        }catch (SignatureException ex){
            throw new BadRequestException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new BadRequestException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new BadRequestException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new BadRequestException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new BadRequestException("JWT claims string is empty.");
        }

	}
}
