package com.lk.blog.blogrestapi.controller;

import javax.validation.Valid;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lk.blog.blogrestapi.domain.dto.CommentDto;
import com.lk.blog.blogrestapi.domain.entity.Comment;
import com.lk.blog.blogrestapi.service.abstracts.CommentService;
import com.lk.blog.blogrestapi.service.decorator.CustomPage;

@RestController
@RequestMapping("/api")
public class CommentController {

	CommentService commentService;
	
	
	public CommentController(CommentService commentService) {
		super();
		this.commentService = commentService;
	}


	@PostMapping("posts/{postId}/comments")
	public ResponseEntity<Comment> createComment(
			@PathVariable("postId") long postId, @Valid @RequestBody Comment comment) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(commentService.createComment(comment, postId));
	}
	
	
	@PostMapping("posts/{postId}/comments-dto")
	public ResponseEntity<CommentDto> createCommentWithDto(
			@PathVariable("postId") long postId, @RequestBody CommentDto commentDto) {
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(commentService.createCommentWithDto(commentDto, postId));
	}
	
	@PutMapping("posts/{postId}/comments/{commentId}")
	public ResponseEntity<Comment> updateComment(
			@PathVariable("postId") long postId, 
			@PathVariable("commentId") long commentId,
			@Valid @RequestBody Comment comment) {
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(commentService.updateComment(postId, commentId, comment));
	}
	
	@GetMapping("/posts/{postId}/comments")
	public CustomPage<Comment> findAll(@PathVariable("postId") long postId, Pageable pageable) {
		return commentService.findAllByPost(postId, pageable);
	}
	
	@GetMapping("/posts/{postId}/comments/{id}")
	public Comment findByPostIdAndCommentId(
			@PathVariable("postId") long postId, 
			@PathVariable("id") long commentId,
			Pageable pageable) {
		return commentService.findById(postId, commentId);
	}
	
	@DeleteMapping("/posts/{postId}/comments/{commentId}")
	public String deleteComment(
			@PathVariable("postId") long postId,
			@PathVariable("commentId") long commentId) {
		
		return commentService.deleteComment(postId, commentId);
	}
}
