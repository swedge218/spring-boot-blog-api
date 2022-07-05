package com.lk.blog.blogrestapi.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lk.blog.blogrestapi.domain.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>{

	Page<Comment> findByPostId(long postId, Pageable pageable);
	Comment findByPostIdAndId(long postId, long commentId);
}
