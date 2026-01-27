package com.syntheticvideo.org.contract

import com.syntheticvideo.org.dto.UserChangePasswordDTO
import com.syntheticvideo.org.dto.UserDeleteDTO
import com.syntheticvideo.org.dto.UserLoginDTO
import com.syntheticvideo.org.dto.UserSaveDTO
import com.syntheticvideo.org.dto.UserStatusChangeDTO
import com.syntheticvideo.org.dto.UserUpdateDTO
import com.syntheticvideo.org.sharedDTOs.UserRespondDTO


interface UserContract {
    suspend fun saveUser(userData: UserSaveDTO): UserRespondDTO
    suspend fun getUserByEmail(email: String): UserRespondDTO
    suspend fun getUserList(): List<UserRespondDTO>
    suspend fun updateUser(user: UserUpdateDTO): UserRespondDTO
    suspend fun deleteUserById(user: UserDeleteDTO): UserRespondDTO
    suspend fun checkUserExists(email: String): Boolean
    suspend fun findUser(userLoginData: UserLoginDTO): UserRespondDTO
    suspend fun findUserById(id: Int): UserRespondDTO
    suspend fun changeUserPassword(userChangePasswordDTO: UserChangePasswordDTO): UserRespondDTO
    suspend fun changeUserStatus(userLogoutData: UserStatusChangeDTO): UserRespondDTO
}
