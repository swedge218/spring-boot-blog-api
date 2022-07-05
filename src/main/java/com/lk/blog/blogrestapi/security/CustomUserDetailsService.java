package com.lk.blog.blogrestapi.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.lk.blog.blogrestapi.domain.entity.Role;
import com.lk.blog.blogrestapi.domain.entity.User;
import com.lk.blog.blogrestapi.domain.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	UserRepository userRepository;
	Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {	
		User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
			.orElseThrow(() -> new UsernameNotFoundException(
					String.format("My User with username %s not found.", usernameOrEmail)));
		
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
		return roles
				.stream()
				.map((role) -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors.toList());
	}
	
}
