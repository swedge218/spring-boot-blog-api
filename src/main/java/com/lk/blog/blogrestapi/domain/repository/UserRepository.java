package com.lk.blog.blogrestapi.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lk.blog.blogrestapi.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	Optional<User> findByUsernameOrEmail(String username, String email);
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
	boolean existsByEmail(String email);
	boolean existsByUsername(String username);
}
