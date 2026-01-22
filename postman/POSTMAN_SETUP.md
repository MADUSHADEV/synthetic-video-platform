# Postman Collection Setup Guide

## 📋 Overview

This guide helps you import and use the Postman collection for the **synthetic-video-platform** (AI Video Library microservices project).

---

## 📥 Import Instructions

### Step 1: Import the Collection
1. Open **Postman**
2. Click **"Import"** button (top left)
3. Navigate to: `synthetic-video-platform.postman_collection.json`
4. Click **Open** → Collection will be imported

### Step 2: Import the Environment
1. Click the **Settings icon** (⚙️) at the top right
2. Select **"Environments"** → **"Import"**
3. Navigate to: `Local_Dev.postman_environment.json`
4. Click **Open** → Environment will be imported

### Step 3: Activate the Environment
1. Click the **Environment dropdown** (currently showing "No Environment")
2. Select **"Local Dev"**

---

## 🌐 API Endpoints

### Identity Service (`/api/v1/identity`)

#### 1️⃣ **Register User** - `POST /register`
- **Authentication**: ❌ Not Required
- **Purpose**: Create a new user account
- **Demo Request Body**:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "SecurePass123!"
}
```
- **Expected Response**: 201 Created
- **Auto-saves**: `user_id`, `user_email`

#### 2️⃣ **Login User** - `POST /login`
- **Authentication**: ❌ Not Required
- **Purpose**: Authenticate and receive JWT token
- **Demo Request Body**:
```json
{
  "email": "john.doe@example.com",
  "password": "SecurePass123!"
}
```
- **Expected Response**: 200 OK
- **Auto-saves**: `auth_token`, `user_id`
- **Important**: This token is automatically saved and used in protected endpoints

#### 3️⃣ **Update User Profile** - `PUT /editUser`
- **Authentication**: ✅ **Required** (JWT Bearer Token)
- **Purpose**: Update user profile information
- **Demo Request Body**:
```json
{
  "id": 1,
  "firstName": "Jonathan",
  "lastName": "Smith",
  "email": "jonathan.smith@example.com"
}
```
- **Expected Response**: 200 OK
- **Note**: Requires logged-in user (runs after Login)

#### 4️⃣ **Change Password** - `PUT /changePassword`
- **Authentication**: ✅ **Required** (JWT Bearer Token)
- **Purpose**: Change user password
- **Demo Request Body**:
```json
{
  "id": 1,
  "email": "john.doe@example.com",
  "newPassword": "NewSecurePass456!"
}
```
- **Expected Response**: 200 OK
- **Note**: Requires logged-in user

#### 5️⃣ **Delete User Account** - `DELETE /deleteUser`
- **Authentication**: ❌ Not Required
- **Purpose**: Permanently delete user account
- **Demo Request Body**:
```json
{
  "id": 1,
  "email": "john.doe@example.com"
}
```
- **Expected Response**: 200 OK

---

## ✅ Password Requirements

Your API has strict password validation. Valid passwords must:
- ✅ Minimum 8 characters
- ✅ At least one **lowercase** letter (a-z)
- ✅ At least one **uppercase** letter (A-Z)
- ✅ At least one **digit** (0-9)
- ✅ At least one **special character** (!@#$%^&*)

### Valid Examples:
- `SecurePass123!`
- `Test@Password2024`
- `MyP@ssw0rd`
- `Admin#2026`

### Invalid Examples:
- `password123` ❌ (no uppercase, no special char)
- `PASSWORD` ❌ (no lowercase, no digit, no special char)
- `Pass1!` ❌ (less than 8 characters)

---

## 🔄 Testing Workflow

### Quick Test (Sequential):
1. **Register User** - Creates new account
2. **Login User** - Gets JWT token (auto-saved)
3. **Update User Profile** - Uses token from step 2
4. **Change Password** - Uses token from step 2
5. **Delete User Account** - Cleans up test user

### Environment Variables Auto-Population:
- After **Register**: `{{user_id}}`, `{{user_email}}` are saved
- After **Login**: `{{auth_token}}`, `{{user_id}}` are saved
- All subsequent requests use these variables automatically

---

## 🔐 Authentication

### Protected Endpoints (Require JWT):
- ✅ `PUT /editUser`
- ✅ `PUT /changePassword`

### How JWT is Used:
1. Get token from `/login` response
2. Token is **automatically saved** to `auth_token` variable
3. Automatically added to `Authorization` header as: `Bearer {{auth_token}}`
4. Used in protected requests

### Manual Token Addition (if needed):
If the auto-save doesn't work, manually add to request headers:
```
Key: Authorization
Value: Bearer <your_jwt_token_here>
```

---

## 📊 Response Format

All responses follow a standard format:
```json
{
  "status": "success",
  "message": "User registered successfully",
  "code": 201,
  "data": {
    "id": 1,
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }
}
```

Or on error:
```json
{
  "status": "error",
  "message": "Validation failed",
  "code": 400,
  "errors": [
    {
      "property": "password",
      "message": "Must match /^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,}$/"
    }
  ]
}
```

---

## 🐛 Common Issues & Solutions

### Issue: "Base URL not found" or 404 errors
- **Solution**: Make sure `Local Dev` environment is selected
- Check that `base_url = http://localhost:8080`

### Issue: "Neither port nor sslPort specified" on server start
- **Solution**: Server isn't running. Start your Ktor application first

### Issue: "The connection attempt failed" (DB error)
- **Solution**: Make sure PostgreSQL Docker container is running
  ```bash
  docker-compose up -d
  ```

### Issue: "Plugin [id: 'io.ktor.plugin'] was not found"
- **Solution**: Update your `build.gradle.kts` with proper plugin configuration

### Issue: 401 Unauthorized on protected endpoints
- **Solution**: 
  1. Run **Login User** first to get token
  2. Check that `auth_token` variable is populated in environment
  3. Verify the token is still valid

### Issue: "Failed to convert request body"
- **Solution**: Check JSON syntax in request body (use JSON formatter)

---

## 🧪 Test Data Examples

### Example 1: Standard User
```json
{
  "firstName": "Alice",
  "lastName": "Johnson",
  "email": "alice.johnson@example.com",
  "password": "Alice@Password123"
}
```

### Example 2: Developer Account
```json
{
  "firstName": "Dev",
  "lastName": "Programmer",
  "email": "dev.programmer@example.com",
  "password": "DevCode#2026"
}
```

### Example 3: Admin User
```json
{
  "firstName": "Admin",
  "lastName": "User",
  "email": "admin@example.com",
  "password": "AdminAccess@2026"
}
```

---

## 🚀 Advanced Features

### Run Collection in Sequence
1. Select the **Identity Service** folder
2. Click **Run** (play icon)
3. Click **Run identity Service** button
4. Tests will run sequentially with auto-saved variables

### Add Request/Response Examples
1. Right-click request → **Add Example**
2. Provide request/response data
3. Useful for documentation

### Create Test Assertions
- Click **Tests** tab in request
- Add validation scripts (examples provided in collection)

---

## 📝 Notes

- **Base URL**: `http://localhost:8080`
- **API Version**: v1
- **Authentication**: JWT Bearer Token
- **Content-Type**: `application/json`
- **Server Port**: 8080 (configurable in `application.conf`)

---

## 📞 Support

For issues or questions:
1. Check the error message in response
2. Verify all required fields are present
3. Ensure password meets requirements
4. Check if server is running and accessible

---

**Last Updated**: January 23, 2026
**Project**: AI Video Library (Synthetic Video Platform)

