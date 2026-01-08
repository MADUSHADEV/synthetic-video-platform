package com.ailibrary.org.contract

import com.ailibrary.org.dto.UserLoginDTO
import com.ailibrary.org.dto.UserSaveDTO
import com.ailibrary.org.sharedDTOs.UserRespondDTO

interface UserContract {
    suspend fun saveUser(userData: UserSaveDTO): UserRespondDTO
    suspend fun getUserByEmail(email: String): UserRespondDTO
    suspend fun updateUser(user: UserSaveDTO): UserRespondDTO
    suspend fun deleteUserByEmail(email: String): UserRespondDTO
    suspend fun checkUserExists(email: String): Boolean
    suspend fun findUser(userLoginData: UserLoginDTO): UserRespondDTO
    suspend fun findUserById(id: Int): UserRespondDTO
}