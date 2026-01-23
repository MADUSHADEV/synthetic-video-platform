package com.ailibrary.org.service

import com.ailibrary.org.dto.UserChangePasswordDTO
import com.ailibrary.org.dto.UserDeleteDTO
import com.ailibrary.org.dto.UserLogOutDTO
import com.ailibrary.org.dto.UserLoginDTO
import com.ailibrary.org.dto.UserSaveDTO
import com.ailibrary.org.dto.UserUpdateDTO
import com.ailibrary.org.exception.UserAlreadyExistsException
import com.ailibrary.org.exception.UserNotFoundByIdException
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
            status = userData.status,
            token = token
        )
    }

    suspend fun EditUser(userUpdateDTO: UserUpdateDTO): UserRespondDTO {
        val checkUserExis: UserRespondDTO = userRepository.findUserById(userUpdateDTO.id)
        checkUserExis.id?.let {
            if (it <= 0) {
                throw UserNotFoundException(userUpdateDTO.email)
            }
        }
        val updatedUserData: UserRespondDTO = userRepository.updateUser(userUpdateDTO)

        //Generate new token with updated user data
        val token: String = JWT.create()
            .withAudience(System.getenv("JWT_AUDIENCE"))
            .withIssuer(System.getenv("JWT_ISSUER"))
            .withArrayClaim("userData", arrayOf(updatedUserData.id.toString(), updatedUserData.email))
            .withExpiresAt(
                java.util.Date(
                    System.currentTimeMillis() + 3_600_000
                )
            )
            .sign(Algorithm.HMAC256(System.getenv("JWT_SECRET")))

        return UserRespondDTO(
            id = updatedUserData.id,
            firstName = updatedUserData.firstName,
            lastName = updatedUserData.lastName,
            email = updatedUserData.email,
            status = updatedUserData.status,
            token = token
        )
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

    suspend fun Logout(userLogOutDTO: UserLogOutDTO): UserRespondDTO {
        val checkUserExist = userRepository.findUserById(userLogOutDTO.id)
        checkUserExist.id?.let {
            if (it <= 0) {
                throw UserNotFoundByIdException(userLogOutDTO.id)
            }
        }
        return userRepository.changeUserStatus(userLogOutDTO)
    }
}