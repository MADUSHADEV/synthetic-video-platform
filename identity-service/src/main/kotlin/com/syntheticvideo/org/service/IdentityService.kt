package com.syntheticvideo.org.service

import com.syntheticvideo.org.dto.UserChangePasswordDTO
import com.syntheticvideo.org.dto.UserDeleteDTO
import com.syntheticvideo.org.dto.UserStatusChangeDTO
import com.syntheticvideo.org.dto.UserLoginDTO
import com.syntheticvideo.org.dto.UserSaveDTO
import com.syntheticvideo.org.dto.UserUpdateDTO
import com.syntheticvideo.org.exception.UserAlreadyExistsException
import com.syntheticvideo.org.exception.UserNotFoundByIdException
import com.syntheticvideo.org.exception.UserNotFoundException
import com.syntheticvideo.org.repository.UserRepository
import com.syntheticvideo.org.sharedDTOs.UserRespondDTO
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

    suspend fun Logout(userStatusChangeDTO: UserStatusChangeDTO): UserRespondDTO {
        val checkUserExist = userRepository.findUserById(userStatusChangeDTO.id)
        checkUserExist.id?.let {
            if (it <= 0) {
                throw UserNotFoundByIdException(userStatusChangeDTO.id)
            }
        }
        return userRepository.changeUserStatus(userStatusChangeDTO)
    }
}
