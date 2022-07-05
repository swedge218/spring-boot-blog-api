package com.lk.blog.blogrestapi.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lk.blog.blogrestapi.domain.dto.PostDto;
import com.lk.blog.blogrestapi.domain.entity.Post;
import com.lk.blog.blogrestapi.domain.enums.CRUDResponse;
import com.lk.blog.blogrestapi.domain.repository.PostRepository;
import com.lk.blog.blogrestapi.exception.ResourceNotFoundException;
import com.lk.blog.blogrestapi.service.abstracts.PostService;
import com.lk.blog.blogrestapi.service.decorator.CustomPage;

@Service
public class PostServiceImpl implements PostService{

	PostRepository postRepository;
	ModelMapper mapper;
	
	@Autowired
	public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
		
		this.postRepository = postRepository;
		this.mapper = mapper;
	}



	@Override
	public Post createPost(Post post) {
		return postRepository.save(post);
	}


	@Override
	public List<PostDto> findAllDto(Pageable pageable) {
		return postRepository.findAll(pageable)
				.stream()
				.map(post -> mapper.map(post, PostDto.class))
				.collect(Collectors.toList());
	}


	@Override
	public Page<Post> findAllPaginated(Pageable pageable) {
		Page<Post> pagedPosts = postRepository.findAll(pageable);
		return new PageImpl<Post>(
				pagedPosts.getContent(), 
				pagedPosts.getPageable(), 
				pagedPosts.getTotalElements());
	}
	
	
	@Override
	public CustomPage<Post> findAll(Pageable pageable) {
		return new CustomPage<Post>(postRepository.findAll(pageable));
	}



	@Override
	public Post findById(long id) {
		return postRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "ID", id));
	}


	@Override
	public PostDto updatePost(PostDto postDto, long id) {
		if(postRepository.findById(id).isPresent()) {
			Post updatedPost = mapper.map(postDto, Post.class);
			updatedPost.setId(id);
			postRepository.save(updatedPost);
			return mapper.map(updatedPost, PostDto.class);
		} else {
			throw new ResourceNotFoundException("Post", "ID", id);
		}
	}
	

	@Override
	public Post updatePostObject(Post postRequest, long id) {		
		return postRepository.findById(id)
			.map(post -> {
				post.setTitle(postRequest.getTitle());
				post.setDescription(postRequest.getDescription());
				post.setContent(postRequest.getContent());				
				return postRepository.save(post);
			})
			.orElseThrow(() -> new ResourceNotFoundException("Post", "ID", id));
	}



	@Override
	public String deleteById(long id) {
		if(postRepository.findById(id).isPresent()) {
			postRepository.deleteById(id);
			return CRUDResponse.DELETED.toString();
		} else {
			throw new ResourceNotFoundException("Post", "ID", id);
		}
	}
	
	
}
