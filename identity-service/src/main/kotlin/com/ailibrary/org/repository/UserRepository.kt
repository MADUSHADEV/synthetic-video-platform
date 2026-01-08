package com.ailibrary.org.repository

import com.ailibrary.org.contract.UserContract
import com.ailibrary.org.db.UserDAO
import com.ailibrary.org.db.daoToSaveDTO
import com.ailibrary.org.db.suspendTransaction
import com.ailibrary.org.dto.UserLoginDTO
import com.ailibrary.org.dto.UserSaveDTO
import com.ailibrary.org.sharedDTOs.UserRespondDTO
import org.mindrot.jbcrypt.BCrypt

class UserRepository : UserContract {
    override suspend fun saveUser(userData: UserSaveDTO): UserRespondDTO = suspendTransaction {

        val hashedPassword: String = BCrypt.hashpw(userData.passwordHash, BCrypt.gensalt())

        val userDAO = UserDAO.new {
            firstName = userData.firstName
            lastName = userData.lastName
            email = userData.email
            passwordHash = hashedPassword
        }

        val userSavedData: UserSaveDTO = daoToSaveDTO(userDAO)

        return@suspendTransaction UserRespondDTO(
            id = userDAO.id.value,
            firstName = userSavedData.firstName,
            lastName = userSavedData.lastName,
            email = userSavedData.email
        )
    }

    override suspend fun getUserByEmail(email: String): UserRespondDTO = suspendTransaction {
        TODO("Not yet implemented")
    }

    override suspend fun updateUser(user: UserSaveDTO): UserRespondDTO = suspendTransaction {
        TODO("Not yet implemented")
    }

    override suspend fun deleteUserByEmail(email: String): UserRespondDTO = suspendTransaction {
        TODO("Not yet implemented")
    }

    override suspend fun checkUserExists(email: String): Boolean = suspendTransaction {
        TODO("Not yet implemented")
    }

    override suspend fun findUser(userLoginData: UserLoginDTO): UserRespondDTO = suspendTransaction {

        val userDAO = UserDAO.find {
            (com.ailibrary.org.db.UserTable.email eq userLoginData.email)
        }.firstOrNull()

        // Check if user exists and password matches
        if (userDAO != null && BCrypt.checkpw(userLoginData.password, userDAO.passwordHash)) {
            return@suspendTransaction UserRespondDTO(
                id = userDAO.id.value,
                firstName = userDAO.firstName,
                lastName = userDAO.lastName,
                email = userDAO.email
            )
        } else {
            throw NoSuchElementException("Invalid email or password")
        }
    }

    override suspend fun findUserById(id: Int): UserRespondDTO = suspendTransaction {
        TODO("Not yet implemented")
    }
}