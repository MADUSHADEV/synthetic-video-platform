package com.ailibrary.org.contract

import com.ailibrary.org.dto.UserChangePasswordDTO
import com.ailibrary.org.dto.UserDeleteDTO
import com.ailibrary.org.dto.UserLoginDTO
import com.ailibrary.org.dto.UserSaveDTO
import com.ailibrary.org.dto.UserStatusChangeDTO
import com.ailibrary.org.dto.UserUpdateDTO
import com.ailibrary.org.sharedDTOs.UserRespondDTO

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