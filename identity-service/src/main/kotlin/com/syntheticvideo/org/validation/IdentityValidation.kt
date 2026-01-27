package com.syntheticvideo.org.validation


import com.syntheticvideo.org.dto.UserChangePasswordDTO
import com.syntheticvideo.org.dto.UserDeleteDTO
import com.syntheticvideo.org.dto.UserLoginDTO
import com.syntheticvideo.org.dto.UserSaveDTO
import com.syntheticvideo.org.dto.UserStatusChangeDTO
import com.syntheticvideo.org.dto.UserUpdateDTO
import org.valiktor.functions.hasSize
import org.valiktor.functions.isEmail
import org.valiktor.functions.isNotBlank
import org.valiktor.functions.isNotEmpty
import org.valiktor.functions.isNotNull
import org.valiktor.functions.matches
import org.valiktor.validate


val PasswordRegex: Regex = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,}$")


fun UserSaveDTO.validateOrThrow() {
    validate(this) {
        validate(UserSaveDTO::email)
            .isEmail<UserSaveDTO>()
            .isNotNull()
            .isNotBlank<UserSaveDTO>()
            .isNotEmpty<UserSaveDTO>()
        validate(UserSaveDTO::firstName)
            .isNotNull()
            .isNotBlank<UserSaveDTO>()
            .isNotEmpty<UserSaveDTO>()
            .hasSize<UserSaveDTO>(min = 1, max = 50)
        validate(UserSaveDTO::lastName)
            .isNotNull()
            .isNotBlank<UserSaveDTO>()
            .isNotEmpty<UserSaveDTO>()
            .hasSize<UserSaveDTO>(min = 1, max = 50)
        validate(UserSaveDTO::password)
            .isNotNull()
            .isNotBlank<UserSaveDTO>()
            .isNotEmpty<UserSaveDTO>()
            .hasSize<UserSaveDTO>(min = 8, max = 100)
            .matches(PasswordRegex)
    }
}

fun UserDeleteDTO.validateOrThrow() {
    validate(this) {
        validate(UserDeleteDTO::email)
            .isEmail<UserDeleteDTO>()
            .isNotNull()
            .isNotBlank<UserDeleteDTO>()
            .isNotEmpty<UserDeleteDTO>()
    }
}

fun UserUpdateDTO.validateOrThrow() {
    validate(this) {
        validate(UserUpdateDTO::email)
            .isEmail<UserUpdateDTO>()
            .isNotNull()
            .isNotBlank<UserUpdateDTO>()
            .isNotEmpty<UserUpdateDTO>()
        validate(UserUpdateDTO::firstName)
            .isNotNull()
            .isNotBlank<UserUpdateDTO>()
            .isNotEmpty<UserUpdateDTO>()
            .hasSize<UserUpdateDTO>(min = 5, max = 50)
        validate(UserUpdateDTO::lastName)
            .isNotNull()
            .isNotBlank<UserUpdateDTO>()
            .isNotEmpty<UserUpdateDTO>()
            .hasSize<UserUpdateDTO>(min = 5, max = 50)
    }
}

fun UserChangePasswordDTO.validateOrThrow() {
    validate(this) {
        validate(UserChangePasswordDTO::newPassword)
            .isNotNull()
            .isNotBlank<UserChangePasswordDTO>()
            .isNotEmpty<UserChangePasswordDTO>()
            .hasSize<UserChangePasswordDTO>(min = 8, max = 100)
            .matches(PasswordRegex)
    }
}

fun UserLoginDTO.validateOrThrow() {
    validate(this) {
        validate(UserLoginDTO::email)
            .isEmail<UserLoginDTO>()
            .isNotNull()
            .isNotBlank<UserLoginDTO>()
            .isNotEmpty<UserLoginDTO>()
        validate(UserLoginDTO::password)
            .isNotNull()
            .isNotBlank<UserLoginDTO>()
            .isNotEmpty<UserLoginDTO>()
            .matches(PasswordRegex)

    }
}

fun UserStatusChangeDTO.validateOrThrow() {
    validate(this) {
        validate(UserStatusChangeDTO::status)
            .isNotNull()
            .isNotBlank<UserStatusChangeDTO>()
            .isNotEmpty<UserStatusChangeDTO>()
    }
}

