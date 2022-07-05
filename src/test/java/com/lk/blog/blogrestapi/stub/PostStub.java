package com.lk.blog.blogrestapi.stub;

import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;
import com.lk.blog.blogrestapi.domain.entity.Comment;
import com.lk.blog.blogrestapi.domain.entity.Post;


public class PostStub {
	//id, title, description, content, comments
	
	
	public Post getPost() {
		Post post = new Post();
		post.setId(1L);
		post.setTitle("title 1");
		post.setDescription("Test Post 1");
		post.setContent("Hashing, equals and toString dropped. Setters and getters only from lombok and using lombok constructors too");
		post.setComments(new HashSet<Comment>());
		
		String commentString = "{\"createdAt\":\"2022-05-25T19:52:22.000+00:00\",\"updatedAt\":\"2022-05-25T19:52:22.000+00:00\",\"id\":1,\"email\":\"post23@lay.com\",\"name\":\"Post23 Lay\",\"body\":\"@JsonIgnore 23 Updated\"}"; 
		Comment comment = new Gson().fromJson(commentString, Comment.class);
		post.getComments().add(comment);
		
		return post;
	}
	
}
