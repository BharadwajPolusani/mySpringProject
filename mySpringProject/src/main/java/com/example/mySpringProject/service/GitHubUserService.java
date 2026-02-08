package com.example.mySpringProject.service;

import com.example.mySpringProject.model.GitHubUserResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

@Service
public class GitHubUserService {

    private static final Logger logger = LoggerFactory.getLogger(GitHubUserService.class);
    private static final String GITHUB_API_BASE_URL = "https://api.github.com/users/";

    private final RestTemplate restTemplate;

    public GitHubUserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GitHubUserResponse getUser(String userName){
        if (userName == null || userName.trim().isEmpty()) {
            logger.warn("Username is null or empty");
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        String url = GITHUB_API_BASE_URL + userName;
        logger.info("Fetching GitHub user data from: {}", url);

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Accept", "application/vnd.github.v3+json");
            headers.set("X-GitHub-Api-Version", "2022-11-28");

            HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

            ResponseEntity<GitHubUserResponse> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    requestEntity,
                    GitHubUserResponse.class
            );

            logger.info("Successfully retrieved GitHub user: {}", userName);
            return response.getBody();

        } catch (HttpClientErrorException.NotFound e) {
            logger.warn("GitHub user not found: {}", userName);
            throw new HttpClientErrorException(e.getStatusCode(), "User not found: " + userName);
        } catch (HttpClientErrorException e) {
            logger.error("GitHub API client error: {} - {}", e.getStatusCode(), e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("Error fetching GitHub user: {}", userName, e);
            throw e;
        }
    }
}

