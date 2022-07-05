package com.lk.blog.blogrestapi.domain.dto;

import java.util.Set;

import com.lk.blog.blogrestapi.domain.dto.abstracts.IDto;
import com.lk.blog.blogrestapi.domain.entity.Comment;

import lombok.Data;
import lombok.Getter;

@Data
public class PostDto implements IDto {

	private Long id;
	private String title;
	private String description;
	private String content;
	private Set<Comment> comments;
	
}
