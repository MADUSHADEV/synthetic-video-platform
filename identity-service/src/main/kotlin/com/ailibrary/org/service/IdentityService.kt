package com.ailibrary.org.service

import com.ailibrary.org.dto.UserChangePasswordDTO
import com.ailibrary.org.dto.UserDeleteDTO
import com.ailibrary.org.dto.UserLoginDTO
import com.ailibrary.org.dto.UserSaveDTO
import com.ailibrary.org.dto.UserUpdateDTO
import com.ailibrary.org.repository.UserRepository
import com.ailibrary.org.sharedDTOs.UserRespondDTO

class IdentityService(
    private val userRepository: UserRepository
) {
    suspend fun SignUp(userSaveDTO: UserSaveDTO): UserRespondDTO {
        val checkUserExis: Boolean = userRepository.checkUserExists(userSaveDTO.email)
        if (checkUserExis) {
            throw IllegalArgumentException("User with email ${userSaveDTO.email} already exists")
        }
        return userRepository.saveUser(userSaveDTO)
    }

    suspend fun Login(userLoginDTO: UserLoginDTO): UserRespondDTO {
        return userRepository.findUser(userLoginDTO)
    }

    suspend fun EditUser(userUpdateDTO: UserUpdateDTO): UserRespondDTO {
        val checkUserExis: Boolean = userRepository.checkUserExists(userUpdateDTO.email)
        if (!checkUserExis) {
            throw IllegalArgumentException("User with email ${userUpdateDTO.email} does not exist")
        }
        return userRepository.updateUser(userUpdateDTO)
    }

    suspend fun ChangePassword(userChangePasswordDTO: UserChangePasswordDTO): UserRespondDTO {
        val checkUserExis: Boolean = userRepository.checkUserExists(userChangePasswordDTO.email)
        if (!checkUserExis) {
            throw IllegalArgumentException("User with email ${userChangePasswordDTO.email} does not exist")
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