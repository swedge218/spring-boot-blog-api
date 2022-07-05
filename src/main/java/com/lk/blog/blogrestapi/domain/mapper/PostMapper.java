package com.lk.blog.blogrestapi.domain.mapper;


import org.springframework.stereotype.Component;

import com.lk.blog.blogrestapi.domain.dto.PostDto;
import com.lk.blog.blogrestapi.domain.entity.Post;

@Component
public class PostMapper {

	public Post toEntity(PostDto postDto) {
		Post post = new Post();
		
		post.setTitle(postDto.getTitle());
		post.setDescription(postDto.getDescription());
		post.setContent(postDto.getContent());
		
		return post;
		
	}

	public PostDto toDto(Post post) {
		PostDto postDto = new PostDto();
		
		postDto.setId(post.getId());
		postDto.setTitle(post.getTitle());
		postDto.setDescription(post.getDescription());
		postDto.setContent(post.getContent());
		
		return postDto;
	}

	
}
