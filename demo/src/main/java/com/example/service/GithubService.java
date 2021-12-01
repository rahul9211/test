package com.example.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.example.model.IssueRequest;

@Service
public interface GithubService {

	Object createIssue(IssueRequest issueRequest, String username, String repositoryName, String issueNumber, String token2);

	Object patchIssue(IssueRequest issueRequest, String username, String repositoryName, String issueNumber,
			String token);

	
}
