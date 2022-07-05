package com.lk.blog.blogrestapi.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.google.gson.Gson;
import com.lk.blog.blogrestapi.BlogRestApiApplication;
import com.lk.blog.blogrestapi.domain.entity.Post;
import com.lk.blog.blogrestapi.gateway.HeaderFactory;
import com.lk.blog.blogrestapi.gateway.TestGateway;
import com.lk.blog.blogrestapi.stub.PostStub;
import com.lk.blog.blogrestapi.stub.TestConstants;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=BlogRestApiApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(Lifecycle.PER_CLASS)
class PostControllerIT {

	@LocalServerPort
	private int port;
	
	private TestGateway gateway;
	
	@Test
	public void itCanGetASinglePostTest() throws Exception {
		String path = "/api/posts/1";
		Post post = new PostStub().getPost();
		String expectedPostString = new Gson().toJson(post);
		
		HttpHeaders headers = new HeaderFactory()
								 .createBasicAuthenticationHeader(TestConstants.USERNAME, TestConstants.PASSWORD )
								.setJSONAccept()
								.build();

		//System.out.println("HEADER STRING " + headers.toString());
		
		gateway = new TestGateway(port);
		ResponseEntity<String> response = gateway.callEndpoint(path, headers);
		Post actualPost = new Gson().fromJson(response.getBody(), Post.class); 
		//let Gson handle the date conversion to match that in expectedPostString
		String actualPostString = new Gson().toJson(actualPost);  
		
		System.out.println("expectedPostString STRING " + expectedPostString);
		System.out.println("response STRING " + actualPostString);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(post.getId(), actualPost.getId());
		assertEquals(post.getComments().size(), actualPost.getComments().size());
		JSONAssert.assertEquals(expectedPostString, actualPostString, false);
	}
	
	
	
//	@Test
//	void itCanGetAllQuestionsTest() throws Exception {
//		String path = "/api/posts";
//		HttpHeaders headers = new HeaderFactory()
//				.setJSONAccept()
//				.build();
//		Post samplePost = new PostStub().getPost();
//		
//		gateway = new TestGateway(port);
//		ResponseEntity<String> response = gateway.callEndpoint(path, headers);
//		
//		Type collectionType = new TypeToken<List<Post>>(){}.getType();
//		List<Post> actualArray = new Gson().fromJson(response.getBody(), collectionType);
//		
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertTrue(actualArray.size() >= 3, String.format("%d must be greater than 3", actualArray.size()));
//		assertTrue(actualArray.size() < 50, String.format("%d must be less than 3", actualArray.size()));
//        assertTrue(actualArray.contains(samplePost), "Set does NOT contains sample Post");
//	}

//	private class PostStub{
//		Post getPost() {
//			Comment comment = new Comment(1L, "jay@jay.com", "Jay Jay", 
//					"Spring boot is such a smooth framework for API development", new Post());
//			
//			Set<Comment> commentSet = new HashSet<>();
//			commentSet.add(comment);
//			
//			return new Post(1L, "title 1", "Test Post", "demo Post", commentSet);
//		}
//	}

}
