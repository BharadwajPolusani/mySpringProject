# Quick Reference - Testing the API

## The Correct Command

```bash
curl http://localhost:8080/api/github/users/octocat
```

## What You Need First

1. **Start the application**:
   ```bash
   cd C:\Users\bhara\Repository\mySpringProject\mySpringProject
   mvn spring-boot:run
   ```

2. **Wait for startup** (look for):
   ```
   Tomcat started on port(s): 8080
   ```

3. **Then in a new terminal**, run:
   ```bash
   curl http://localhost:8080/api/github/users/octocat
   ```

---

## If curl doesn't work on Windows

Use PowerShell instead:
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/github/users/octocat"
```

Or just open in browser:
```
http://localhost:8080/api/github/users/octocat
```

---

## Test Cases

```bash
# Get valid user
curl http://localhost:8080/api/github/users/octocat

# Get another user
curl http://localhost:8080/api/github/users/torvalds

# Test invalid user (should get 404)
curl http://localhost:8080/api/github/users/thisuserdoesnotexist999
```

---

## Expected Response

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

---

## Common Issues

| Issue | Solution |
|-------|----------|
| `curl: command not found` | Use PowerShell: `Invoke-WebRequest -Uri "http://localhost:8080/api/github/users/octocat"` |
| `Connection refused` | Make sure application is running with `mvn spring-boot:run` |
| `404 Not Found` | Check URL is exactly: `http://localhost:8080/api/github/users/octocat` |
| `port 8080 in use` | Change port: Edit `application.properties` and set `server.port=8081` |


