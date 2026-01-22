# 🚀 Postman Quick Reference

## Files to Import
1. **Collection**: `synthetic-video-platform.postman_collection.json`
2. **Environment**: `Local_Dev.postman_environment.json`

---

## 📌 Endpoint Summary

| # | Method | Endpoint | Auth | Test Priority |
|---|--------|----------|------|---|
| 1 | POST | `/api/v1/identity/register` | ❌ | 🥇 **First** |
| 2 | POST | `/api/v1/identity/login` | ❌ | 🥈 **Second** |
| 3 | PUT | `/api/v1/identity/editUser` | ✅ JWT | 🥉 **Third** |
| 4 | PUT | `/api/v1/identity/changePassword` | ✅ JWT | 4️⃣ **Fourth** |
| 5 | DELETE | `/api/v1/identity/deleteUser` | ❌ | 5️⃣ **Fifth** |

---

## 🎯 Quick Test Sequence

```
1. Register User
   ↓ (saves user_id, user_email)
2. Login User
   ↓ (saves auth_token)
3. Update Profile
   ↓ (uses auth_token)
4. Change Password
   ↓ (uses auth_token)
5. Delete Account
   ↓ (cleanup)
```

---

## 🔑 Password Requirements

```regex
^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[^\da-zA-Z]).{8,}$
```

✅ **Valid**: `SecurePass123!`, `Test@2026`, `MyP@ss0rd`  
❌ **Invalid**: `password`, `PASSWORD123`, `Pass1`

---

## 📦 Request Bodies

### Register
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "password": "SecurePass123!"
}
```

### Login
```json
{
  "email": "john.doe@example.com",
  "password": "SecurePass123!"
}
```

### Edit User
```json
{
  "id": 1,
  "firstName": "Jonathan",
  "lastName": "Smith",
  "email": "jonathan.smith@example.com"
}
```

### Change Password
```json
{
  "id": 1,
  "email": "john.doe@example.com",
  "newPassword": "NewSecurePass456!"
}
```

### Delete User
```json
{
  "id": 1,
  "email": "john.doe@example.com"
}
```

---

## 🔐 Protected Endpoints

**Headers Required**:
```
Authorization: Bearer {{auth_token}}
Content-Type: application/json
```

**Endpoints**:
- `PUT /editUser` ✅
- `PUT /changePassword` ✅

---

## 🌍 Environment Variables

| Variable | Value | Auto-Populated |
|----------|-------|---|
| `base_url` | `http://localhost:8080` | ❌ Manual |
| `auth_token` | JWT from login | ✅ After login |
| `user_id` | User ID | ✅ After register/login |
| `user_email` | Email | ✅ After register/login |

---

## ✨ Features

✅ Auto-save JWT token after login  
✅ Auto-populate user variables  
✅ Pre-configured request bodies  
✅ Error handling examples  
✅ Sequential test workflow  

---

**Ready to test? Import the files and select "Local Dev" environment!** 🎉

