package com.example.controller;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.IssueRequest;
import com.example.service.GithubService;

@RestController
public class GitHubController {
	
	@Autowired
	GithubService GithubService;

	private static final Logger LOGGER = LoggerFactory.getLogger(GitHubController.class);
	
	@PostMapping(path = "/create-issues/{issueNumber}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createIssueRecord(@RequestBody IssueRequest issueRequest,
			@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "repositoryName", required = false) String repositoryName,
			@PathVariable(name="issueNumber") String issueNumber, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		

		Object gitIssueRes = GithubService.createIssue(issueRequest,username,repositoryName,issueNumber,token);

		LOGGER.info("Exit::createCustomerRecord::save the person record.");
		return new ResponseEntity<>(gitIssueRes, HttpStatus.CREATED);

	}
	
	@PatchMapping(path = "/create-issues/{issueNumber}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> patchIssueRecord(@RequestBody IssueRequest issueRequest,
			@RequestParam(name = "username", required = false) String username,
			@RequestParam(name = "repositoryName", required = false) String repositoryName,
			@PathVariable(name="issueNumber") String issueNumber, HttpServletRequest request) {

		String token = request.getHeader("Authorization");
		

		Object gitIssueRes = GithubService.patchIssue(issueRequest,username,repositoryName,issueNumber,token);

		LOGGER.info("Exit::createCustomerRecord::save the person record.");
		return new ResponseEntity<>(gitIssueRes, HttpStatus.CREATED);

	}
	
}
