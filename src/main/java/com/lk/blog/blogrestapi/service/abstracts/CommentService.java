package com.lk.blog.blogrestapi.service.abstracts;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.lk.blog.blogrestapi.domain.dto.CommentDto;
import com.lk.blog.blogrestapi.domain.entity.Comment;
import com.lk.blog.blogrestapi.service.decorator.CustomPage;

public interface CommentService {

	Comment createComment(Comment comment, long postId);
	CommentDto createCommentWithDto(CommentDto commentDto, long postId);
	Comment updateComment(long postId, long commentId, Comment comment);
	CustomPage<Comment> findAllByPost(long postId, Pageable pageable);
	Comment findById(long postId, long commentId);
	String deleteComment(long postId, long commentId);
}
