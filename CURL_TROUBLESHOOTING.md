# Curl Testing Guide

## Issue: Curl Command Not Working

### Common Reasons & Solutions

---

## 1. **Application Not Running**

**Check**: Is the Spring Boot application running?

**Start the application**:
```bash
cd C:\Users\bhara\Repository\mySpringProject\mySpringProject
mvn clean install
mvn spring-boot:run
```

**Expected output**:
```
Started MySpringProjectApplication in X.XXX seconds
Tomcat started on port(s): 8080 (http)
```

---

## 2. **Wrong Port or URL**

**Correct URL** (after removing context-path):
```bash
curl http://localhost:8080/api/github/users/octocat
```

**NOT** these (these are WRONG):
```bash
curl http://localhost:8080/api/api/github/users/octocat  # Wrong - double /api
curl http://localhost:8080/github/users/octocat          # Wrong - missing /api prefix
curl http://localhost:8080/users/octocat                 # Wrong - missing /api/github
```

---

## 3. **Curl Not Found (Windows)**

If you see: `'curl' is not recognized as an internal or external command`

**Option A: Use PowerShell Invoke-WebRequest**
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/github/users/octocat"
```

**Option B: Install curl**
- Download from: https://curl.se/download.html
- Or use Git Bash (comes with curl)

**Option C: Use Postman/Insomnia**
- Download free from https://www.postman.com
- Import the endpoint and test

---

## 4. **Connection Refused**

**Error**: `curl: (7) Failed to connect to localhost port 8080: Connection refused`

**Solutions**:
1. Make sure application is running (see #1)
2. Check if another app is using port 8080:
   ```bash
   netstat -ano | findstr :8080
   ```
3. Change port in application.properties if 8080 is busy:
   ```properties
   server.port=8081
   ```

---

## 5. **404 Not Found Error**

**Error**: `{"status":404,"error":"Not Found"}`

**Reasons**:
- URL path is wrong (see #2)
- Spring didn't load the controller
- Wrong HTTP method (use GET, not POST)

**Verify endpoint is registered**:
1. Check logs for "Mapped" messages
2. Add debug logging to controller
3. Try: `http://localhost:8080/helloworld` (HelloController endpoint)

---

## 6. **GitHub API Rate Limit**

**Error**: Response with 403 or 429 status

**Solution**: Wait a few minutes and try again. GitHub API has rate limits.

---

## Testing Steps (In Order)

### Step 1: Verify Application is Running
```bash
curl -v http://localhost:8080/helloworld
```
**Expected**: Should see "Hello World!" response

### Step 2: Test GitHub Endpoint
```bash
curl http://localhost:8080/api/github/users/octocat
```
**Expected**: JSON with user info

### Step 3: Test with Different Users
```bash
curl http://localhost:8080/api/github/users/github
curl http://localhost:8080/api/github/users/torvalds
```

### Step 4: Test Invalid User
```bash
curl http://localhost:8080/api/github/users/invaliduser12345xyz
```
**Expected**: 404 error with error response format

---

## Complete Test Commands

### Using curl (Command Prompt/PowerShell)
```bash
# Windows Command Prompt
curl http://localhost:8080/api/github/users/octocat

# Windows PowerShell
Invoke-WebRequest -Uri "http://localhost:8080/api/github/users/octocat"
```

### Using PowerShell (Better for Windows)
```powershell
$response = Invoke-WebRequest -Uri "http://localhost:8080/api/github/users/octocat"
$response.Content | ConvertFrom-Json | Format-List
```

### Using Postman
```
Method: GET
URL: http://localhost:8080/api/github/users/octocat
Headers: (none required)
Body: (none)
Send
```

---

## Expected Successful Response

**Status**: 200 OK

**Body**:
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

## Expected Error Response

**Status**: 404 Not Found

**Body**:
```json
{
  "code": "HTTP_CLIENT_ERROR",
  "message": "User not found: invaliduser12345xyz",
  "status": 404,
  "timestamp": "2026-02-07T10:30:45.123456"
}
```

---

## Debugging Tips

### Check if Application Started Successfully
Look for these logs when starting:
```
Started MySpringProjectApplication in X.XXX seconds
Tomcat started on port(s): 8080 (http)
```

### Monitor Controller Logs
```
2026-02-07 10:30:45 - Fetching GitHub user information for username: octocat
2026-02-07 10:30:46 - Successfully fetched GitHub user: octocat
```

### Enable More Verbose Output
Edit `application.properties`:
```properties
logging.level.org.springframework.web=DEBUG
logging.level.org.springframework.boot.web=DEBUG
```

### Use Browser Console
Simply visit in browser:
```
http://localhost:8080/api/github/users/octocat
```

Browser will show JSON response in a friendly format.

---

## Quick Checklist

- [ ] Application is running (`mvn spring-boot:run`)
- [ ] Check logs show "Started in X seconds"
- [ ] Port 8080 is correct (or changed in properties)
- [ ] Using correct URL: `http://localhost:8080/api/github/users/octocat`
- [ ] Using GET method, not POST
- [ ] GitHub API is accessible (test in browser)
- [ ] No other app using port 8080

---

## Still Not Working?

1. **Share the error message** you're seeing
2. **Check the full curl output**:
   ```bash
   curl -v http://localhost:8080/api/github/users/octocat
   ```
3. **Check application logs** for startup errors
4. **Verify internet connection** to GitHub API
5. **Restart IDE** if Spring processes seem stuck


