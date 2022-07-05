package com.lk.blog.blogrestapi.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lk.blog.blogrestapi.domain.dto.PostDto;
import com.lk.blog.blogrestapi.domain.entity.Post;
import com.lk.blog.blogrestapi.service.abstracts.PostService;
import com.lk.blog.blogrestapi.service.decorator.CustomPage;

@RestController
@RequestMapping(value="/api/posts")
public class PostController {

	@Autowired
	PostService postService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Post> createPost(@Valid @RequestBody Post post) {
		return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(post));
	}
	
	
	@GetMapping
	public List<PostDto> findAllDto(Pageable pageable){
		return postService.findAllDto(pageable);
	}
	
	
	@GetMapping("/paginated")
	public Page<Post> findAllPaginated(Pageable pageable){
		return postService.findAllPaginated(pageable);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/custom-paginated")
	public CustomPage<Post> findAll(Pageable pageable){
		return postService.findAll(pageable);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Post> findById(@PathVariable("id") long id){
		return ResponseEntity.ok(postService.findById(id));
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/dto/{id}")
	public ResponseEntity<PostDto> updatePost(
			@RequestBody PostDto postDto, @PathVariable("id") long id) {
		
		return ResponseEntity.status(HttpStatus.OK).body(postService.updatePost(postDto, id));
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Post> updatePostObject(
			@Valid @RequestBody Post post, 
			@PathVariable("id") long id) {
		
		return ResponseEntity.status(HttpStatus.OK).body(postService.updatePostObject(post, id));
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePost(@PathVariable("id") long id) {
		return ResponseEntity.status(HttpStatus.OK).body(postService.deleteById(id));
	}
}
