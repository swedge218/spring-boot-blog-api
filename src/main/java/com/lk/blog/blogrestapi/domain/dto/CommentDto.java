package com.lk.blog.blogrestapi.domain.dto;

import com.lk.blog.blogrestapi.domain.entity.Post;

import lombok.Data;

@Data
public class CommentDto {
	
	private long id;
	private String email;
	private String name;
	private String body;
//	private Post post;
}
