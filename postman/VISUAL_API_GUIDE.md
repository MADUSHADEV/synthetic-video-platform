# 📖 Visual API Testing Guide

## 🎯 Your API at a Glance

```
┌─────────────────────────────────────────────────────┐
│         SYNTHETIC VIDEO PLATFORM SERVICES           │
│                                                     │
│  🔐 IDENTITY SERVICE (Currently Active)            │
│     Base URL: http://localhost:8080                │
│     Version: v1                                    │
└─────────────────────────────────────────────────────┘
```

---

## 🗺️ API Endpoint Map

```
http://localhost:8080/api/v1/identity
│
├─ POST /register
│  ├─ Auth: ❌ Not Required
│  ├─ Input: UserSaveDTO
│  └─ Response: 201 Created
│
├─ POST /login
│  ├─ Auth: ❌ Not Required
│  ├─ Input: UserLoginDTO
│  ├─ Response: 200 OK + JWT Token
│  └─ Auto-saves: auth_token, user_id
│
├─ PUT /editUser
│  ├─ Auth: ✅ JWT Bearer Token
│  ├─ Input: UserUpdateDTO
│  └─ Response: 200 OK
│
├─ PUT /changePassword
│  ├─ Auth: ✅ JWT Bearer Token
│  ├─ Input: UserChangePasswordDTO
│  └─ Response: 200 OK
│
└─ DELETE /deleteUser
   ├─ Auth: ❌ Not Required
   ├─ Input: UserDeleteDTO
   └─ Response: 200 OK
```

---

## 📲 Complete Request/Response Examples

### REQUEST 1️⃣: Register New User

```
METHOD:  POST
URL:     http://localhost:8080/api/v1/identity/register
HEADERS: Content-Type: application/json

BODY:
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "SecurePass123!"
}

RESPONSE (201 Created):
{
  "status": "success",
  "message": "User registered successfully",
  "code": 201,
  "data": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "createdAt": "2026-01-23T10:30:00Z"
  }
}

✅ SAVES TO ENVIRONMENT:
   - user_id = 1
   - user_email = john.doe@example.com
```

---

### REQUEST 2️⃣: Login & Get JWT Token

```
METHOD:  POST
URL:     http://localhost:8080/api/v1/identity/login
HEADERS: Content-Type: application/json

BODY:
{
  "email": "john.doe@example.com",
  "password": "SecurePass123!"
}

RESPONSE (200 OK):
{
  "status": "success",
  "message": "User logged in successfully",
  "code": 200,
  "data": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "expiresIn": 3600
  }
}

✅ AUTO-SAVES TO ENVIRONMENT:
   - auth_token = eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
   - user_id = 1
```

---

### REQUEST 3️⃣: Update User Profile (Protected)

```
METHOD:  PUT
URL:     http://localhost:8080/api/v1/identity/editUser
HEADERS:
  - Content-Type: application/json
  - Authorization: Bearer {{auth_token}}  ← Auto-populated

BODY:
{
  "id": {{user_id}},
  "firstName": "Jonathan",
  "lastName": "Smith",
  "email": "jonathan.smith@example.com"
}

RESPONSE (200 OK):
{
  "status": "success",
  "message": "User updated successfully",
  "code": 200,
  "data": {
    "id": 1,
    "firstName": "Jonathan",
    "lastName": "Smith",
    "email": "jonathan.smith@example.com",
    "updatedAt": "2026-01-23T11:00:00Z"
  }
}
```

---

### REQUEST 4️⃣: Change Password (Protected)

```
METHOD:  PUT
URL:     http://localhost:8080/api/v1/identity/changePassword
HEADERS:
  - Content-Type: application/json
  - Authorization: Bearer {{auth_token}}  ← Auto-populated

BODY:
{
  "id": {{user_id}},
  "email": "{{user_email}}",
  "newPassword": "NewSecurePass456!"
}

RESPONSE (200 OK):
{
  "status": "success",
  "message": "Password changed successfully",
  "code": 200,
  "data": {
    "id": 1,
    "message": "Password updated successfully",
    "changedAt": "2026-01-23T11:15:00Z"
  }
}
```

---

### REQUEST 5️⃣: Delete User Account

```
METHOD:  DELETE
URL:     http://localhost:8080/api/v1/identity/deleteUser
HEADERS: Content-Type: application/json

BODY:
{
  "id": {{user_id}},
  "email": "{{user_email}}"
}

RESPONSE (200 OK):
{
  "status": "success",
  "message": "User deleted successfully",
  "code": 200,
  "data": {
    "id": 1,
    "message": "User account deleted permanently"
  }
}
```

---

## 🔐 Authentication Flow

```
┌─────────────────────────────────────────────────────┐
│          AUTHENTICATION SEQUENCE                    │
└─────────────────────────────────────────────────────┘

Step 1: LOGIN
┌──────────────────┐
│ POST /login      │
│ Credentials      │
└────────┬─────────┘
         │
         ↓
    JWT Generated
         │
         ↓
Step 2: SAVE TOKEN
┌──────────────────┐
│ auth_token var   │
│ is populated     │
│ automatically    │
└────────┬─────────┘
         │
         ↓
Step 3: USE IN PROTECTED ROUTES
┌──────────────────┐
│ Authorization:   │
│ Bearer token     │
│ in Header        │
└────────┬─────────┘
         │
         ↓
Step 4: REQUEST SUCCEEDS ✅
```

---

## 🎨 Postman Setup Checklist

```
☐ Import synthetic-video-platform.postman_collection.json
  → File Menu → Import → Select JSON file

☐ Import Local_Dev.postman_environment.json
  → Settings (⚙️) → Environments → Import → Select JSON file

☐ Select Environment
  → Environment Dropdown (top-right) → Select "Local Dev"

☐ Verify Server Running
  → http://localhost:8080 should be accessible

☐ Start Testing
  → Click "Register User" in collection
  → Follow sequential order
  → Watch variables auto-populate
```

---

## 📊 Variable Lifecycle

```
START
  │
  ├─→ [Register User]
  │     └─→ SAVES: user_id, user_email
  │
  ├─→ [Login User]
  │     └─→ SAVES: auth_token, user_id
  │
  ├─→ [Edit User] (uses auth_token ✅)
  │     └─→ Updates profile
  │
  ├─→ [Change Password] (uses auth_token ✅)
  │     └─→ Updates password
  │
  ├─→ [Delete User]
  │     └─→ Removes account
  │
END
```

---

## 🔑 Password Validation Regex

```
Pattern: ^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\da-zA-Z]).{8,}$

Breaking Down:
  (?=.*[a-z])       → Must contain lowercase letter
  (?=.*[A-Z])       → Must contain UPPERCASE letter
  (?=.*\d)          → Must contain digit (0-9)
  (?=.*[^\da-zA-Z]) → Must contain special character
  .{8,}             → Minimum 8 characters

✅ VALID PASSWORDS:
   - SecurePass123!
   - Test@Password2024
   - MyP@ssw0rd
   - Admin#2026Password
   - Dev123@Code

❌ INVALID PASSWORDS:
   - password123        (no uppercase, no special char)
   - PASSWORD           (no lowercase, no digit, no special char)
   - Pass1!             (less than 8 characters)
   - pass@word1         (no uppercase)
   - PASSWORD1@         (no lowercase)
```

---

## 📈 Expected Response Structure

### Success Response
```json
{
  "status": "success",
  "message": "Action completed successfully",
  "code": 200 | 201,
  "data": {
    // Response data here
  }
}
```

### Error Response
```json
{
  "status": "error",
  "message": "Operation failed",
  "code": 400 | 401 | 500,
  "errors": [
    {
      "property": "fieldName",
      "message": "Error description"
    }
  ]
}
```

---

## 🛠️ Testing Tools Available

### Option 1: Postman (Recommended)
- ✅ Visual interface
- ✅ Auto-saves environment variables
- ✅ Built-in test scripts
- ✅ Easy to follow workflow

### Option 2: cURL (CLI)
- ✅ Fast command-line testing
- ✅ Scripts for automation
- ✅ Minimal dependencies

### Option 3: PowerShell (Windows)
- ✅ Native Windows support
- ✅ Easy scripting
- ✅ Invoke-RestMethod built-in

---

## 🚀 Recommended Testing Order

```
1️⃣  REGISTER USER
    └─ Creates test account
    └─ Saves: user_id, user_email

2️⃣  LOGIN USER
    └─ Gets JWT token
    └─ Saves: auth_token

3️⃣  UPDATE PROFILE
    └─ Uses token from step 2
    └─ Tests PUT + Auth

4️⃣  CHANGE PASSWORD
    └─ Uses token from step 2
    └─ Tests new password

5️⃣  DELETE ACCOUNT
    └─ Cleanup test data
    └─ Verification
```

---

## 💡 Pro Tips

### Tip 1: Monitor Network Traffic
- Open browser DevTools (F12)
- Go to Network tab
- Run Postman requests
- See actual HTTP calls

### Tip 2: Test Error Cases
- Wrong password → 401 error
- Duplicate email → 400 error
- Missing fields → 400 error
- Invalid JWT → 401 error

### Tip 3: Save Multiple Environments
- Create "Production" environment
- Create "Testing" environment
- Switch between them easily

### Tip 4: Use Collection Variables
- Defined at collection level
- Overridden by environment variables
- Useful for global settings

---

## 📞 Quick Troubleshooting

| Problem | Solution |
|---------|----------|
| **Connection Refused** | Start Ktor server: `./gradlew :identity-service:run` |
| **404 Not Found** | Check `base_url` in environment (should be `http://localhost:8080`) |
| **401 Unauthorized** | Run Login endpoint first to get `auth_token` |
| **Bad Request** | Check JSON syntax in request body |
| **Password Validation** | Use format: `SecurePass123!` |
| **Token Expired** | Login again to get new token |
| **Database Error** | Start PostgreSQL: `docker-compose up -d` |

---

**Ready to Test?** 🎉

1. Import JSON files into Postman
2. Select "Local Dev" environment
3. Click "Register User"
4. Follow the workflow

Good luck! 🚀

