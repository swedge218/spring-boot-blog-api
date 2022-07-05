package com.lk.blog.blogrestapi.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	CustomUserDetailsService customUserDetails;
	
	@Qualifier("${blogapp.jwt.provider.name}")
	TokenProvider tokenProvider;

//	@Autowired
//	public void setTokenProvider(ApplicationContext context) {
//		System.out.println("Provider Name: " + providerQualifierName);
//		tokenProvider = (TokenProvider) context.getBean(providerQualifierName);
//	}
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, 
			HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		
		// extract token from request header
		String token = extractTokenFromHeader(request);
		System.out.println("Token " + token);
		
		// validate the token
		if(StringUtils.hasText(token) && tokenProvider.isValid(token)) {
			// extract username from valid token
			String username = tokenProvider.getUserNameFromToken(token);
			
			// load (Custom) Userdetails with the username
			UserDetails userDetails = customUserDetails.loadUserByUsername(username);
			
			// set up auth token - this constructor sets authenticated = true since 
			// the accessToken has already been validated.
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
           
            // set up security context
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		
		// set filterchain
		filterChain.doFilter(request, response);
		
	}

	private String extractTokenFromHeader(HttpServletRequest request) {
		String authHeaderValue = request.getHeader("Authorization");
		
		if(StringUtils.hasText(authHeaderValue) && StringUtils.startsWithIgnoreCase(authHeaderValue, "Bearer ")) {
			return authHeaderValue.split(" ")[1].trim();
			// return authHeaderValue.substring(7, authHeaderValue.length());
		}
		
		return null;
	}
	
}
