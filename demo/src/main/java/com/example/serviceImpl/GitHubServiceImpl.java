package com.example.serviceImpl;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.model.IssueRequest;
import com.example.service.GithubService;

@Component
public class GitHubServiceImpl implements GithubService{

	@Value("${github.url.base}")
	private String githubBaseUrl;
	@Value("${github.issue}")
	private String githubIssueNumber;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private HttpHeaders headerCreator(String token) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", token);
		return headers;
	}
	
	@Override
	public Object createIssue(IssueRequest issueRequest, String username, String repositoryName,String issueNumber, String token) {
		
		
		ResponseEntity<Object> responseEntity = null;
		try {
			Map<String, String> uriVariables = new HashMap<>();
			uriVariables.put("username", username);
			uriVariables.put("repositoryName", repositoryName);
			uriVariables.put("issueNumber", issueNumber);
			
			URI targetUrl = UriComponentsBuilder.fromUriString(githubBaseUrl).path(githubIssueNumber).buildAndExpand(uriVariables)
					.encode().toUri();

			HttpHeaders headers = headerCreator(token);
			HttpEntity<IssueRequest> entity = new HttpEntity<>(issueRequest, headers);
			responseEntity = restTemplate.exchange(targetUrl, HttpMethod.POST, entity, Object.class);

		} catch (Exception ex) {
			throw ex;
		}

		return responseEntity.getBody();
	}

	@Override
	public Object patchIssue(IssueRequest issueRequest, String username, String repositoryName, String issueNumber,
			String token) {
		ResponseEntity<Object> responseEntity = null;
		try {
			Map<String, String> uriVariables = new HashMap<>();
			uriVariables.put("username", username);
			uriVariables.put("repositoryName", repositoryName);
			uriVariables.put("issueNumber", issueNumber);
			
			URI targetUrl = UriComponentsBuilder.fromUriString(githubBaseUrl).path(githubIssueNumber).buildAndExpand(uriVariables)
					.encode().toUri();

			HttpHeaders headers = headerCreator(token);
			HttpEntity<IssueRequest> entity = new HttpEntity<>(issueRequest, headers);
			responseEntity = restTemplate.exchange(targetUrl, HttpMethod.PATCH, entity, Object.class);

		} catch (Exception ex) {
			throw ex;
		}

		return responseEntity.getBody();
	}

}
