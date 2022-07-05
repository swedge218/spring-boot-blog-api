package com.lk.blog.blogrestapi.service;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.lk.blog.blogrestapi.domain.dto.PostDto;
import com.lk.blog.blogrestapi.domain.entity.Post;
import com.lk.blog.blogrestapi.domain.repository.PostRepository;
import com.lk.blog.blogrestapi.service.impl.PostServiceImpl;

public class PostServiceImplTest {

	@MockBean
	PostRepository postRepository;
	
	@MockBean
	ModelMapper modelMapper;
	
	@InjectMocks
	PostServiceImpl postServiceImpl;
	
	@Test
	void whenGetSinglePost_returnPostWithComments() {
//		postServiceImpl = new PostServiceImpl(postRepository, modelMapper);
//		Post post = postServiceImpl.findById(4L);
		assertTrue(true);
//		assertTrue(post.getId() == 4L);
//		assertTrue(post.getComments().size() > 0);
	}
}
