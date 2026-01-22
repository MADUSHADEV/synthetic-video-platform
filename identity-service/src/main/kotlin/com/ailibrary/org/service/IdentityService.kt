package com.ailibrary.org.service

import com.ailibrary.org.dto.UserChangePasswordDTO
import com.ailibrary.org.dto.UserDeleteDTO
import com.ailibrary.org.dto.UserLoginDTO
import com.ailibrary.org.dto.UserSaveDTO
import com.ailibrary.org.dto.UserUpdateDTO
import com.ailibrary.org.exception.UserAlreadyExistsException
import com.ailibrary.org.exception.UserNotFoundException
import com.ailibrary.org.repository.UserRepository
import com.ailibrary.org.sharedDTOs.UserRespondDTO
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm

class IdentityService(
    private val userRepository: UserRepository
) {
    suspend fun SignUp(userSaveDTO: UserSaveDTO): UserRespondDTO {
        val checkUserExis: Boolean = userRepository.checkUserExists(userSaveDTO.email)
        if (checkUserExis) {
            throw UserAlreadyExistsException(userSaveDTO.email)
        }
        return userRepository.saveUser(userSaveDTO)
    }


    suspend fun Login(userLoginDTO: UserLoginDTO): UserRespondDTO {

        val userData: UserRespondDTO = userRepository.findUser(userLoginDTO)

        // Generate token or session here if needed
        val token: String = JWT.create()
            .withAudience(System.getenv("JWT_AUDIENCE"))
            .withIssuer(System.getenv("JWT_ISSUER"))
            .withArrayClaim("userData", arrayOf(userData.id.toString(), userData.email))
            .withExpiresAt(
                java.util.Date(
                    System.currentTimeMillis() + 3_600_000
                )
            )
            .sign(Algorithm.HMAC256(System.getenv("JWT_SECRET")))

        // Return user data along with the token
        return UserRespondDTO(
            id = userData.id,
            firstName = userData.firstName,
            lastName = userData.lastName,
            email = userData.email,
            token = token
        )
    }

    suspend fun EditUser(userUpdateDTO: UserUpdateDTO): UserRespondDTO {
        val checkUserExis: Boolean = userRepository.checkUserExists(userUpdateDTO.email)
        if (!checkUserExis) {
            throw UserNotFoundException(userUpdateDTO.email)
        }
        return userRepository.updateUser(userUpdateDTO)
    }

    suspend fun ChangePassword(userChangePasswordDTO: UserChangePasswordDTO): UserRespondDTO {
        val checkUserExis: Boolean = userRepository.checkUserExists(userChangePasswordDTO.email)
        if (!checkUserExis) {
            throw UserNotFoundException(userChangePasswordDTO.email)
        }
        return userRepository.changeUserPassword(userChangePasswordDTO)
    }

    suspend fun GetUserList(): List<UserRespondDTO> {
        return userRepository.getUserList()
    }

    suspend fun DeleteUser(user: UserDeleteDTO): UserRespondDTO {
        return userRepository.deleteUserById(user)
    }

}