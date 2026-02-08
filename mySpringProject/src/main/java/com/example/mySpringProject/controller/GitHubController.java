package com.example.mySpringProject.controller;

import com.example.mySpringProject.model.GitHubUserResponse;
import com.example.mySpringProject.service.GitHubUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/github")
public class GitHubController {

    private static final Logger logger = LoggerFactory.getLogger(GitHubController.class);

    private final GitHubUserService githubUserService;

    public GitHubController(GitHubUserService githubUserService) {
        this.githubUserService = githubUserService;
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<GitHubUserResponse> getUser(@PathVariable String username) {
        logger.info("Fetching GitHub user information for username: {}", username);

        // Service will validate and throw exceptions if needed
        // GlobalExceptionHandler will catch and format errors
        GitHubUserResponse user = githubUserService.getUser(username);
        logger.info("Successfully fetched GitHub user: {}", username);
        return ResponseEntity.ok(user);
    }
}

