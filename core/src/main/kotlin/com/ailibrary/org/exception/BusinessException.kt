package com.ailibrary.org.exception

import io.ktor.http.HttpStatusCode

// base class for business exceptions
sealed class BusinessException(message: String, val statusCode: HttpStatusCode) : Exception(message)

// specific business exception
class UserNotFoundException(email: String) :
    BusinessException(
        "User with email $email not found",
        HttpStatusCode.NotFound
    )

class UserNotFoundByIdException(id: Int) : BusinessException(
    "User with id $id not found",
    HttpStatusCode.NotFound
)

class InvalidCredentialsException : BusinessException(
    "Invalid email or password",
    HttpStatusCode.Unauthorized
)

class UserAlreadyExistsException(email: String) : BusinessException(
    "User with email $email already exists",
    HttpStatusCode.Conflict
)

