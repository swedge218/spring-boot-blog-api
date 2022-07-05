package com.lk.blog.blogrestapi.runner;

import org.springframework.boot.CommandLineRunner;

import com.lk.blog.blogrestapi.domain.repository.CommentRepository;
import com.lk.blog.blogrestapi.domain.repository.PostRepository;

public class MyCommandLineRunner implements CommandLineRunner {

	PostRepository postRepository;
	CommentRepository commentRepository;
	
	
	@Override
	public void run(String... args) throws Exception {
		
		
	}

	
}
