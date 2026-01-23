package com.ailibrary.org.repository

import com.ailibrary.org.contract.UserContract
import com.ailibrary.org.db.*
import com.ailibrary.org.dto.*
import com.ailibrary.org.exception.*
import com.ailibrary.org.sharedDTOs.UserRespondDTO
import org.jetbrains.exposed.sql.and
import org.mindrot.jbcrypt.BCrypt

class UserRepository : UserContract {
    override suspend fun saveUser(userData: UserSaveDTO): UserRespondDTO = suspendTransaction {

        val hashedPassword: String = BCrypt.hashpw(userData.password, BCrypt.gensalt())

        val activeStatus = UserStatusDAO.find { UserStatusTable.name eq "Active" }.firstOrNull()
            ?: throw IllegalStateException("Default status 'Active' not found in database")

        val userDAO = UserDAO.new {
            firstName = userData.firstName
            lastName = userData.lastName
            email = userData.email
            passwordHash = hashedPassword
            status = activeStatus
        }

        return@suspendTransaction daoToResponseDTO(userDAO)
    }

    override suspend fun getUserByEmail(email: String): UserRespondDTO = suspendTransaction {
        val userDAO = UserDAO.find { UserTable.email eq email }.limit(1).firstOrNull()
            ?: throw UserNotFoundException(email)

        return@suspendTransaction daoToResponseDTO(userDAO)
    }

    override suspend fun updateUser(user: UserUpdateDTO): UserRespondDTO = suspendTransaction {
        val userDAO = UserDAO.findByIdAndUpdate(user.id) {
            it.email = user.email
            it.firstName = user.firstName
            it.lastName = user.lastName
        } ?: throw UserNotFoundByIdException(user.id)

        return@suspendTransaction daoToResponseDTO(userDAO)

    }

    override suspend fun deleteUserById(user: UserDeleteDTO): UserRespondDTO = suspendTransaction {
        val userDAO = UserDAO.find { (UserTable.id eq user.id) and (UserTable.email eq user.email) }
            .firstOrNull() ?: throw UserNotFoundByIdException(user.id)

        val response = daoToResponseDTO(userDAO)
        userDAO.delete()

        return@suspendTransaction response
    }

    override suspend fun checkUserExists(email: String): Boolean = suspendTransaction {
        return@suspendTransaction UserDAO.find { (UserTable.email eq email) }.count() > 0
    }

    override suspend fun findUser(userLoginData: UserLoginDTO): UserRespondDTO = suspendTransaction {

        val userDAO = UserDAO.find {
            (UserTable.email eq userLoginData.email)
        }.firstOrNull()

        // Check if user exists and password matches
        if (userDAO != null && BCrypt.checkpw(userLoginData.password, userDAO.passwordHash)) {
            return@suspendTransaction daoToResponseDTO(userDAO)
        } else {
            throw InvalidCredentialsException()
        }
    }

    override suspend fun findUserById(id: Int): UserRespondDTO = suspendTransaction {
        val userDAO = UserDAO.findById(id)
            ?: throw UserNotFoundByIdException(id)

        return@suspendTransaction daoToResponseDTO(userDAO)
    }

    override suspend fun getUserList(): List<UserRespondDTO> {
        return suspendTransaction {
            UserDAO.all().map { userDAO ->
                daoToResponseDTO(userDAO)
            }
        }
    }

    override suspend fun changeUserPassword(userChangePasswordDTO: UserChangePasswordDTO): UserRespondDTO =
        suspendTransaction {
            val userDAO = UserDAO.findById(userChangePasswordDTO.id)
                ?: throw UserNotFoundByIdException(userChangePasswordDTO.id)
            val hashedPassword: String = BCrypt.hashpw(userChangePasswordDTO.newPassword, BCrypt.gensalt())

            userDAO.passwordHash = hashedPassword

            return@suspendTransaction daoToResponseDTO(userDAO)
        }

    override suspend fun changeUserStatus(userLogoutData: UserStatusChangeDTO): UserRespondDTO = suspendTransaction {
        val userDAO = UserDAO.findById(userLogoutData.id)
            ?: throw UserNotFoundByIdException(userLogoutData.id)

        val statusDAO = UserStatusDAO.find { UserStatusTable.name eq userLogoutData.status }.firstOrNull()
            ?: throw IllegalStateException("Status '${userLogoutData.status}' not found in database")

        userDAO.status = statusDAO

        return@suspendTransaction daoToResponseDTO(userDAO)
    }
}