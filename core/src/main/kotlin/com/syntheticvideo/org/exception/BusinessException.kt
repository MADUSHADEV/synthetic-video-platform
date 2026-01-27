package com.syntheticvideo.org.exception

import io.ktor.http.HttpStatusCode

// base class for business exceptions
sealed class BusinessException(message: String, val statusCode: HttpStatusCode) : Exception(message)

// specific business exception
class UserNotFoundException(email: String) :
    com.syntheticvideo.org.exception.BusinessException(
        "User with email $email not found",
        HttpStatusCode.NotFound
    )

class UserNotFoundByIdException(id: Int) : com.syntheticvideo.org.exception.BusinessException(
    "User with id $id not found",
    HttpStatusCode.NotFound
)

class InvalidCredentialsException : com.syntheticvideo.org.exception.BusinessException(
    "Invalid email or password",
    HttpStatusCode.Unauthorized
)

class UserAlreadyExistsException(email: String) : com.syntheticvideo.org.exception.BusinessException(
    "User with email $email already exists",
    HttpStatusCode.Conflict
)

class UserStatusNotFoundException(status: String) : com.syntheticvideo.org.exception.BusinessException(
    "User status '$status' not found",
    HttpStatusCode.NotFound
)


