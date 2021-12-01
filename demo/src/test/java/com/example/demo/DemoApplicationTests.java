package com.example.demo;

import org.json.JSONException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.example.model.GitRes;
import com.example.model.IssueRequest;

class DemoApplicationTests {

	final String resourceUrl = "https://api.github.com/repos/rahul9211/test/issues";

	@Test
	public void gitGetIssueTest() {
		final HttpHeaders headers = prepareBasicAuthHeaders();
		final ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
		final RestTemplate template = new RestTemplate(requestFactory);
		final ResponseEntity<GitRes> response = template.exchange(resourceUrl + "/1", HttpMethod.GET,
				new HttpEntity<>(headers), GitRes.class);

		Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void gitPostTest() {
		final HttpHeaders headers = prepareBasicAuthHeaders();
		final ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
		final RestTemplate template = new RestTemplate(requestFactory);

		final HttpEntity<IssueRequest> request = new HttpEntity<>(new IssueRequest("new title", "new Issue Body"),
				headers);
		final GitRes res = template.postForObject(resourceUrl + "/1", request, GitRes.class);
		Assertions.assertNotNull(res);
		Assertions.assertEquals(res.getTitle(), "new title");
	}

	@Test
	public void gitPatchTest() throws JSONException {
		final HttpHeaders headers = prepareBasicAuthHeaders();
		IssueRequest issue = new IssueRequest("updated title", null);
		final HttpEntity<IssueRequest> request = new HttpEntity<>(issue, headers);

		final ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
		final RestTemplate template = new RestTemplate(requestFactory);

		template.patchForObject(resourceUrl+"/1", request, Object.class);

		// Check that Resource was updated
		final ResponseEntity<GitRes> updateResponse = template.exchange(resourceUrl+"/1", HttpMethod.GET,
				new HttpEntity<>(headers), GitRes.class);
		System.out.println(updateResponse.getBody().toString());
		Assertions.assertEquals(updateResponse.getBody().getTitle(), issue.getTitle());
	}

	ClientHttpRequestFactory getClientHttpRequestFactory() {
		final int timeout = 5;
		final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(timeout * 1000);
		return clientHttpRequestFactory;
	}

	private HttpHeaders prepareBasicAuthHeaders() {
		final HttpHeaders headers = new HttpHeaders();
		final String gitToken = "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";
		headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + gitToken);
		return headers;
	}
}
