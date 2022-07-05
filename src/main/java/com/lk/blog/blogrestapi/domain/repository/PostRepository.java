package com.lk.blog.blogrestapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lk.blog.blogrestapi.domain.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long>{
	
}
