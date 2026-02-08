# Windows Testing Guide

## The Issue You're Facing

On Windows, `curl` might not be available or might not work as expected in Command Prompt. Here are your best options:

---

## Option 1: PowerShell (Easiest - Built into Windows)

### Basic Request
```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/github/users/octocat"
```

### View Response Nicely
```powershell
$response = Invoke-WebRequest -Uri "http://localhost:8080/api/github/users/octocat"
$response.Content | ConvertFrom-Json | Format-List
```

### Short Alias (Faster)
```powershell
iwr "http://localhost:8080/api/github/users/octocat"
```

---

## Option 2: Browser (Simplest)

Just paste this in your browser address bar:
```
http://localhost:8080/api/github/users/octocat
```

The response will show as JSON in the browser.

---

## Option 3: Git Bash (If you have Git installed)

Git comes with curl built-in:
```bash
curl http://localhost:8080/api/github/users/octocat
```

1. Install Git: https://git-scm.com/download/win
2. Open "Git Bash"
3. Run curl commands

---

## Option 4: Download curl

1. Download: https://curl.se/download.html
2. Extract to `C:\curl\`
3. Add to PATH or use full path:
   ```
   C:\curl\curl.exe http://localhost:8080/api/github/users/octocat
   ```

---

## Step-by-Step: PowerShell Method

### Step 1: Open PowerShell
- Press `Win + R`
- Type `powershell`
- Press Enter

### Step 2: Test Connection
```powershell
iwr http://localhost:8080/helloworld
```
Should show: `Hello World!`

### Step 3: Test GitHub API
```powershell
iwr http://localhost:8080/api/github/users/octocat
```

### Step 4: Pretty Print JSON (Optional)
```powershell
$response = iwr http://localhost:8080/api/github/users/octocat
$response.Content | ConvertFrom-Json | Format-List
```

---

## Step-by-Step: Browser Method

### Step 1: Make Sure App is Running
```bash
mvn spring-boot:run
```

### Step 2: Open Browser
- Chrome, Edge, Firefox, Safari - any browser

### Step 3: Navigate to
```
http://localhost:8080/api/github/users/octocat
```

### Step 4: View Response
Browser will display the JSON response. It might look like:
```json
{"login":"octocat","id":1,"name":"The Octocat",...}
```

---

## Complete Test Script (PowerShell)

Copy and paste this entire script into PowerShell:

```powershell
# Test 1: Check if application is running
Write-Host "Test 1: Hello World endpoint..."
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/helloworld" -ErrorAction Stop
    Write-Host "✓ App is running!" -ForegroundColor Green
    Write-Host "Response: $($response.Content)"
} catch {
    Write-Host "✗ App is not running. Start it with: mvn spring-boot:run" -ForegroundColor Red
    exit
}

# Test 2: Get valid GitHub user
Write-Host "`nTest 2: Fetching octocat user..."
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/github/users/octocat" -ErrorAction Stop
    Write-Host "✓ Success!" -ForegroundColor Green
    Write-Host "Response:"
    $response.Content | ConvertFrom-Json | Format-List
} catch {
    Write-Host "✗ Error: $($_.Exception.Message)" -ForegroundColor Red
}

# Test 3: Test error handling with invalid user
Write-Host "`nTest 3: Testing invalid user (should get 404)..."
try {
    $response = Invoke-WebRequest -Uri "http://localhost:8080/api/github/users/invaliduser12345xyz" -ErrorAction Stop
} catch {
    Write-Host "✓ Got expected error: $($_.Exception.Response.StatusCode)" -ForegroundColor Green
    Write-Host "Error details:"
    $_.Exception.Response | Format-List
}

Write-Host "`n✓ All tests completed!" -ForegroundColor Green
```

---

## Troubleshooting

### "Cannot find a parameter with name 'Uri'"
You're using an old PowerShell version. Update PowerShell:
```powershell
# Install latest PowerShell
winget install Microsoft.PowerShell
```

Or use older syntax:
```powershell
Invoke-WebRequest "http://localhost:8080/api/github/users/octocat"
```

### "Connection refused"
App is not running. Execute:
```bash
cd C:\Users\bhara\Repository\mySpringProject\mySpringProject
mvn spring-boot:run
```

### "404 Not Found"
Check your URL carefully:
- ✓ Correct: `http://localhost:8080/api/github/users/octocat`
- ✗ Wrong: `http://localhost:8080/github/users/octocat` (missing /api)
- ✗ Wrong: `http://localhost:8080/api/github/user/octocat` (should be /users)

---

## Recommended Approach for Windows

1. **Use PowerShell** (already on your machine, no install needed)
2. **Or use Browser** (simplest, no setup required)
3. **Or use Postman** (best for frequent testing)

---

## Windows Terminal (Modern Alternative)

If you have Windows Terminal:
```powershell
# Open Windows Terminal
winget install Microsoft.WindowsTerminal

# Then use PowerShell as described above
iwr http://localhost:8080/api/github/users/octocat
```

---

## Video Alternative

If you prefer video instructions:
1. Open browser
2. Go to: `http://localhost:8080/api/github/users/octocat`
3. Browser shows response

**That's it!** No command line needed.


