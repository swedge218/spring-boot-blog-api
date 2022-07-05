package com.lk.blog.blogrestapi.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lk.blog.blogrestapi.domain.dto.JwtAuthResponse;
import com.lk.blog.blogrestapi.domain.dto.LoginDto;
import com.lk.blog.blogrestapi.domain.dto.SignUpDto;
import com.lk.blog.blogrestapi.domain.dto.UserDto;
import com.lk.blog.blogrestapi.domain.entity.Role;
import com.lk.blog.blogrestapi.domain.entity.User;
import com.lk.blog.blogrestapi.domain.repository.RoleRepository;
import com.lk.blog.blogrestapi.domain.repository.UserRepository;
import com.lk.blog.blogrestapi.exception.BadRequestException;
import com.lk.blog.blogrestapi.security.JwtTokenProvider;
import com.lk.blog.blogrestapi.security.TokenProvider;
import com.lk.blog.blogrestapi.service.abstracts.AuthService;

@Service
public class AuthServiceImpl implements AuthService {
	
	AuthenticationManager authenticationManager;
	
	UserRepository userRepository;
	
	RoleRepository roleRepository;
	
	ModelMapper mapper;
	
	PasswordEncoder passwordEncoder;
	
//	JwtTokenProvider tokenProvider;
	
	@Autowired
	@Qualifier("${blogapp.jwt.provider.name}")
	TokenProvider tokenProvider;
	
	
	public AuthServiceImpl(AuthenticationManager authenticationManager, 
			UserRepository userRepository,
			RoleRepository roleRepository,
			ModelMapper mapper,
			PasswordEncoder passwordEncoder) {
		
		super();
		this.authenticationManager = authenticationManager;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.mapper = mapper;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	public JwtAuthResponse authenticateUser(LoginDto loginDto) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				loginDto.getUsername(), loginDto.getPassword());
		
		/**
		 * The authenticate method "must be" doing the comparison of user supplied password (loginDto.getPassword())
		 * and the password from DB CustomuserDetails::loaduserByUsername (called by this authenticate method)
		 * called in the background. CustomuserDetails implements UserDetailsService - Spring Dependency management at work.
		 */
		Authentication authentication = authenticationManager.authenticate(authenticationToken);
		
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        User user = userRepository.findByUsernameOrEmail(
        		loginDto.getUsername(), loginDto.getUsername()).get();
        
//        User user = (User) authentication.getPrincipal();
        
        JwtAuthResponse jwtAuthResponse = mapper.map(user, JwtAuthResponse.class);
        String accessToken = tokenProvider.generateToken(authentication);
        jwtAuthResponse.setAccessToken(accessToken);
        
        return jwtAuthResponse;

	}


	@Override
	public UserDto signUpUser(SignUpDto signUpDto) {
		if(userRepository.existsByUsername(signUpDto.getUsername())) {
			throw new BadRequestException("Auth", String.format("Username '%s' already exists", signUpDto.getUsername()));
		}
		
		if(userRepository.existsByEmail(signUpDto.getEmail())){
			throw new BadRequestException("Auth", String.format("Email '%s' already exists", signUpDto.getEmail()));
		}
		
		if(!roleRepository.existsByName(signUpDto.getRoleName())) {
			throw new BadRequestException("Auth", String.format("Role '%s' does not exist", signUpDto.getRoleName()));
		}
		
		Role role = roleRepository.findByName(signUpDto.getRoleName()).get();
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		
		
		User user = mapper.map(signUpDto, User.class);
		user.setRoles(roles);
		user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		User newUser = userRepository.save(user);
		
		return mapper.map(newUser, UserDto.class);
		
	}

	
	
}
