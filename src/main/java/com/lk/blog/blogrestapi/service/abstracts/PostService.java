package com.lk.blog.blogrestapi.service.abstracts;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.lk.blog.blogrestapi.domain.dto.PostDto;
import com.lk.blog.blogrestapi.domain.entity.Post;
import com.lk.blog.blogrestapi.service.decorator.CustomPage;

public interface PostService {

	public Post createPost(Post post);
	
	List<PostDto> findAllDto(Pageable pageable);
	
	Page<Post> findAllPaginated(Pageable pageable);
	
	CustomPage<Post> findAll(Pageable pageable);
	
	public Post findById(long id);
	
	PostDto updatePost(PostDto postDto, long id);
	
	Post updatePostObject(Post post, long id);
	
	String deleteById(long id);
}
