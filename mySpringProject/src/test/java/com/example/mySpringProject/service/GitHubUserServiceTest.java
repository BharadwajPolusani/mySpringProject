package com.example.mySpringProject.service;

import com.example.mySpringProject.model.GitHubUserResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GitHubUserServiceTest {

    @Mock
    private RestTemplate restTemplate;

    private GitHubUserService githubUserService;

    @BeforeEach
    public void setUp() {
        githubUserService = new GitHubUserService(restTemplate);
    }

    @Test
    public void testGetUserSuccess() {
        // Arrange
        String username = "octocat";
        GitHubUserResponse expectedResponse = new GitHubUserResponse();
        expectedResponse.setLogin("octocat");
        expectedResponse.setId(1);
        expectedResponse.setName("The Octocat");

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(GitHubUserResponse.class)
        )).thenReturn(new ResponseEntity<>(expectedResponse, HttpStatus.OK));

        // Act
        GitHubUserResponse response = githubUserService.getUser(username);

        // Assert
        assertNotNull(response);
        assertEquals("octocat", response.getLogin());
        assertEquals(1, response.getId());
        assertEquals("The Octocat", response.getName());
    }

    @Test
    public void testGetUserNotFound() {
        // Arrange
        String username = "nonexistentuser";

        when(restTemplate.exchange(
                anyString(),
                eq(HttpMethod.GET),
                any(HttpEntity.class),
                eq(GitHubUserResponse.class)
        )).thenThrow(new HttpClientErrorException(HttpStatus.NOT_FOUND));

        // Act & Assert
        assertThrows(HttpClientErrorException.class, () -> {
            githubUserService.getUser(username);
        });
    }

    @Test
    public void testGetUserWithNullUsername() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            githubUserService.getUser(null);
        });
    }

    @Test
    public void testGetUserWithEmptyUsername() {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            githubUserService.getUser("");
        });
    }
}

