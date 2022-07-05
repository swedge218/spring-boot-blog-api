package com.lk.blog.blogrestapi.gateway;

import java.util.List;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;


public class TestGateway {
	
	private int port;
	
	private String baseUrl = "http://localhost";
	
	private TestRestTemplate restTemplate = new TestRestTemplate(); 

	public TestGateway(int p) {
		this.port = p;
	}

	private String createUrl(String path) {
		return baseUrl + ":" + port + path;
	}
	
	public ResponseEntity<String> callEndpoint(String path, HttpHeaders headers) {
		String url = createUrl(path);
		
		HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
		
		return response;
	}

	
	public ResponseEntity<String> callPostEndpoint(String path, String body, HttpHeaders headers) {
		String url = createUrl(path);
				
		HttpEntity<String> requestEntity = new HttpEntity<String>(body, headers);
		
		ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
		
		return response;
	}
	
	public ResponseEntity<List<String>> callListEndpoint(String path, HttpHeaders headers) {
		String url = createUrl(path);
		
		HttpEntity<String> requestEntity = new HttpEntity<String>(null, headers);
		
		ResponseEntity<List<String>> response = restTemplate.exchange(
				url, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<String>>(){});
		
		return response;
	}
}