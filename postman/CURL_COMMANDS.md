# cURL Commands Reference

If you prefer testing with cURL instead of Postman, use these commands:

---

## 1️⃣ Register User

```bash
curl -X POST http://localhost:8080/api/v1/identity/register \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "password": "SecurePass123!"
  }'
```

---

## 2️⃣ Login User

```bash
curl -X POST http://localhost:8080/api/v1/identity/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "john.doe@example.com",
    "password": "SecurePass123!"
  }'
```

**Save the token from response**:
```bash
TOKEN="<paste_token_here>"
```

---

## 3️⃣ Update User Profile

```bash
TOKEN="your_jwt_token_here"

curl -X PUT http://localhost:8080/api/v1/identity/editUser \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "id": 1,
    "firstName": "Jonathan",
    "lastName": "Smith",
    "email": "jonathan.smith@example.com"
  }'
```

---

## 4️⃣ Change Password

```bash
TOKEN="your_jwt_token_here"

curl -X PUT http://localhost:8080/api/v1/identity/changePassword \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer $TOKEN" \
  -d '{
    "id": 1,
    "email": "john.doe@example.com",
    "newPassword": "NewSecurePass456!"
  }'
```

---

## 5️⃣ Delete User

```bash
curl -X DELETE http://localhost:8080/api/v1/identity/deleteUser \
  -H "Content-Type: application/json" \
  -d '{
    "id": 1,
    "email": "john.doe@example.com"
  }'
```

---

## 🔧 Windows PowerShell Commands

If using PowerShell on Windows, use this syntax instead:

### Register User (PowerShell)
```powershell
$body = @{
    firstName = "John"
    lastName = "Doe"
    email = "john.doe@example.com"
    password = "SecurePass123!"
} | ConvertTo-Json

Invoke-RestMethod -Uri http://localhost:8080/api/v1/identity/register `
  -Method POST `
  -ContentType "application/json" `
  -Body $body
```

### Login User (PowerShell)
```powershell
$body = @{
    email = "john.doe@example.com"
    password = "SecurePass123!"
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri http://localhost:8080/api/v1/identity/login `
  -Method POST `
  -ContentType "application/json" `
  -Body $body

$token = $response.data.token
Write-Output "Token: $token"
```

### Update Profile (PowerShell)
```powershell
$token = "your_jwt_token_here"

$body = @{
    id = 1
    firstName = "Jonathan"
    lastName = "Smith"
    email = "jonathan.smith@example.com"
} | ConvertTo-Json

Invoke-RestMethod -Uri http://localhost:8080/api/v1/identity/editUser `
  -Method PUT `
  -ContentType "application/json" `
  -Headers @{Authorization = "Bearer $token"} `
  -Body $body
```

### Change Password (PowerShell)
```powershell
$token = "your_jwt_token_here"

$body = @{
    id = 1
    email = "john.doe@example.com"
    newPassword = "NewSecurePass456!"
} | ConvertTo-Json

Invoke-RestMethod -Uri http://localhost:8080/api/v1/identity/changePassword `
  -Method PUT `
  -ContentType "application/json" `
  -Headers @{Authorization = "Bearer $token"} `
  -Body $body
```

### Delete User (PowerShell)
```powershell
$body = @{
    id = 1
    email = "john.doe@example.com"
} | ConvertTo-Json

Invoke-RestMethod -Uri http://localhost:8080/api/v1/identity/deleteUser `
  -Method DELETE `
  -ContentType "application/json" `
  -Body $body
```

---

## 📋 Copy-Paste Ready Commands

### Register
```bash
curl -X POST http://localhost:8080/api/v1/identity/register -H "Content-Type: application/json" -d '{"firstName":"John","lastName":"Doe","email":"john.doe@example.com","password":"SecurePass123!"}'
```

### Login
```bash
curl -X POST http://localhost:8080/api/v1/identity/login -H "Content-Type: application/json" -d '{"email":"john.doe@example.com","password":"SecurePass123!"}'
```

### Update Profile
```bash
curl -X PUT http://localhost:8080/api/v1/identity/editUser -H "Content-Type: application/json" -H "Authorization: Bearer YOUR_TOKEN_HERE" -d '{"id":1,"firstName":"Jonathan","lastName":"Smith","email":"jonathan.smith@example.com"}'
```

### Change Password
```bash
curl -X PUT http://localhost:8080/api/v1/identity/changePassword -H "Content-Type: application/json" -H "Authorization: Bearer YOUR_TOKEN_HERE" -d '{"id":1,"email":"john.doe@example.com","newPassword":"NewSecurePass456!"}'
```

### Delete User
```bash
curl -X DELETE http://localhost:8080/api/v1/identity/deleteUser -H "Content-Type: application/json" -d '{"id":1,"email":"john.doe@example.com"}'
```

---

**Tip**: For the protected endpoints, replace `YOUR_TOKEN_HERE` with the actual JWT token from the login response.

