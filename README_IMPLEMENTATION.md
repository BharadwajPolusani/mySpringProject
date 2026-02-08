# GitHub User API - Spring Boot Project

## Overview
This Spring Boot application provides a REST API to fetch GitHub user information and includes comprehensive error handling, logging, and unit tests.

## Features Implemented

### 1. **GitHub User Controller** (`GitHubController`)
- **Endpoint**: `GET /api/github/users/{username}`
- **Description**: Fetches GitHub user information by username
- **Response**: Returns a `GitHubUserResponse` object with user details
- **Example**:
  ```
  GET http://localhost:8080/api/github/users/octocat
  ```

### 2. **Enhanced GitHub User Service** (`GitHubUserService`)
- Fetches user data from GitHub API with proper headers
- Validates input (username cannot be null or empty)
- Includes comprehensive logging at INFO and DEBUG levels
- Specific error handling for HTTP 404 (Not Found)
- Graceful error propagation for other API errors

### 3. **Global Exception Handler** (`GlobalExceptionHandler`)
Handles the following exceptions with appropriate HTTP status codes:
- **HttpClientErrorException**: 4xx errors from GitHub API
- **HttpServerErrorException**: 5xx errors from GitHub API
- **ResourceAccessException**: Connection/network issues (503 Service Unavailable)
- **IllegalArgumentException**: Invalid input (400 Bad Request)
- **General Exception**: Unhandled errors (500 Internal Server Error)

Each error response includes:
- Error code
- Descriptive message
- HTTP status code
- Timestamp

### 4. **Unit Tests** (`GitHubUserServiceTest`)
Comprehensive test coverage including:
- ✅ Successful user retrieval
- ✅ User not found (404) scenario
- ✅ Null username validation
- ✅ Empty username validation

Run tests with:
```bash
mvn test
```

### 5. **Configuration**
**application.properties** settings:
- Logging level: DEBUG for application code, INFO for root logger
- Server port: 8080
- Context path: /api
- RestTemplate timeouts: 5 seconds (connection and read)

## API Endpoints

### Get GitHub User
```
GET /api/github/users/{username}
```

**Success Response (200 OK)**:
```json
{
  "login": "octocat",
  "id": 1,
  "name": "The Octocat",
  "avatarUrl": "https://avatars.githubusercontent.com/u/1?v=4",
  "publicRepos": 8,
  "followers": 3938,
  "createdAt": "2011-01-25T18:44:36Z"
}
```

**Error Response (404 Not Found)**:
```json
{
  "code": "HTTP_CLIENT_ERROR",
  "message": "User not found: invaliduser",
  "status": 404,
  "timestamp": "2026-02-07T10:30:45.123456"
}
```

**Error Response (400 Bad Request)**:
```json
{
  "code": "INVALID_ARGUMENT",
  "message": "Username cannot be null or empty",
  "status": 400,
  "timestamp": "2026-02-07T10:30:45.123456"
}
```

## Project Structure
```
src/
├── main/
│   ├── java/com/example/mySpringProject/
│   │   ├── controller/
│   │   │   ├── GitHubController.java      (NEW)
│   │   │   ├── complianceController.java  (UPDATED)
│   │   │   └── HelloController.java
│   │   ├── service/
│   │   │   └── GitHubUserService.java     (ENHANCED)
│   │   ├── exception/
│   │   │   ├── GlobalExceptionHandler.java (NEW)
│   │   │   └── ErrorResponse.java         (NEW)
│   │   ├── model/
│   │   │   └── GitHubUserResponse.java
│   │   └── config/
│   │       └── RestTemplateConfig.java
│   └── resources/
│       └── application.properties          (UPDATED)
└── test/
    └── java/com/example/mySpringProject/
        └── service/
            └── GitHubUserServiceTest.java  (NEW)
```

## Running the Application

### Build
```bash
mvn clean build
```

### Run
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Test Endpoints
```bash
# Get a GitHub user
curl http://localhost:8080/api/github/users/octocat

# Test invalid user
curl http://localhost:8080/api/github/users/nonexistentuser12345

# Test empty username
curl http://localhost:8080/api/github/users/
```

## Dependencies
- Spring Boot 3.5.7
- Spring Web
- Spring AOP
- JUnit 5 (Jupiter)
- Mockito
- RestTemplate

## Logging
Logs are written to console with the following format:
```
2026-02-07 10:30:45 - [ThreadName] Class.method() - Message
```

Debug logs are available for the `com.example.mySpringProject` package for development.

## Next Steps
1. ✅ GitHub User Controller implemented
2. ✅ Error handling implemented
3. ✅ Unit tests implemented
4. ⏳ Compliance Controller - Ready for BPMN process integration
5. ⏳ Integration tests - Add integration test suite
6. ⏳ API documentation - Add Swagger/SpringDoc OpenAPI

## Future Enhancements
- Add caching for GitHub user requests
- Implement rate limiting
- Add authentication/authorization
- Integrate Camunda BPMN for compliance workflows
- Add API documentation with Swagger
- Implement CI/CD pipeline

