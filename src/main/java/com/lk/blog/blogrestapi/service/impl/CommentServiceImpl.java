package com.lk.blog.blogrestapi.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.lk.blog.blogrestapi.domain.constant.CRUDConstants;
import com.lk.blog.blogrestapi.domain.dto.CommentDto;
import com.lk.blog.blogrestapi.domain.entity.Comment;
import com.lk.blog.blogrestapi.domain.entity.Post;
import com.lk.blog.blogrestapi.domain.enums.CRUDResponse;
import com.lk.blog.blogrestapi.domain.repository.CommentRepository;
import com.lk.blog.blogrestapi.domain.repository.PostRepository;
import com.lk.blog.blogrestapi.exception.BadRequestException;
import com.lk.blog.blogrestapi.exception.BlogApiException;
import com.lk.blog.blogrestapi.exception.ResourceNotFoundException;
import com.lk.blog.blogrestapi.service.abstracts.CommentService;
import com.lk.blog.blogrestapi.service.decorator.CustomPage;

@Service
public class CommentServiceImpl implements CommentService {

	CommentRepository commentRepository;
	PostRepository postRepository;
	ModelMapper mapper;
	
	
	public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, ModelMapper mapper) {
		super();
		this.commentRepository = commentRepository;
		this.postRepository = postRepository;
		this.mapper = mapper;
	}


	@Override
	public Comment createComment(Comment comment, long postId) {
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Post", "ID", postId));
		
		comment.setPost(post);
		Comment newComment = commentRepository.save(comment);
		
//		post.getComments().add(newComment);
//		postRepository.save(post);
		return newComment;
	}
	
	
	

	@Override
	public CommentDto createCommentWithDto(CommentDto commentDto, long postId) {
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Post", "ID", postId));
		
		Comment comment = mapper.map(commentDto, Comment.class);
		comment.setPost(post);
		commentRepository.save(comment);
		
//		post.getComments().add(newComment);
//		postRepository.save(post);
		
		return mapper.map(comment, CommentDto.class);
	}


	@Override
	public Comment updateComment(long postId, long commentId, Comment commentReq) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
		
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));
		
		if(comment.getPost().getId() != post.getId()) {
			throw new BadRequestException("Comment", "Comment not post child");
		}
		
		commentReq.setId(commentId);
		commentReq.setPost(post);
		post.getComments().add(comment);
		return commentRepository.save(commentReq);
	}


	@Override
	public CustomPage<Comment> findAllByPost(long postId, Pageable pageable) {
		Post post = postRepository.findById(postId).orElseThrow(
				() -> new ResourceNotFoundException("Post", "ID", postId));
		return CustomPage.setup(commentRepository.findByPostId(postId, pageable));
	}


	@Override
	public Comment findById(long postId, long commentId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
		
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));
		
		if(comment.getPost().getId() != post.getId()) {
			throw new BadRequestException("Comment", "Comment not post child");
		}
			
		return comment;		
	}

	
	@Override
	public String deleteComment(long postId, long commentId) {
		checkParamsWellFormed(postId, commentId);
		commentRepository.deleteById(commentId);
		return CRUDConstants.DELETED_MESSAGE;
	}
	
	private boolean checkParamsWellFormed(long postId, long commentId) {
		Post post = postRepository.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "ID", postId));
		
		Comment comment = commentRepository.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "ID", commentId));
		
		if(comment.getPost().getId() != post.getId()) {
			throw new BadRequestException("Comment", "Comment not post child");
		}
		
		return true;
	}
	
}
