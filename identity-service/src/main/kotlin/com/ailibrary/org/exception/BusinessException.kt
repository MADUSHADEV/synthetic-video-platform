package com.ailibrary.org.exception

// base class for business exceptions
sealed class BusinessException(message: String) : Exception(message)

// specific business exception
class UserNotFoundException(email: String) : BusinessException("User with email $email not found")
class UserNotFoundByIdException(id: Int) : BusinessException("User with id $id not found")
class InvalidCredentialsException : BusinessException("Invalid email or password")
class UserAlreadyExistsException(email: String) : BusinessException("User with email $email already exists")

