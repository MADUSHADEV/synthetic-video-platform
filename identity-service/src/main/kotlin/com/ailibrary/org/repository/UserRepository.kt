package com.ailibrary.org.repository

import com.ailibrary.org.contract.UserContract
import com.ailibrary.org.db.UserDAO
import com.ailibrary.org.db.UserTable
import com.ailibrary.org.db.daoToSaveDTO
import com.ailibrary.org.db.suspendTransaction
import com.ailibrary.org.dto.UserChangePasswordDTO
import com.ailibrary.org.dto.UserDeleteDTO
import com.ailibrary.org.dto.UserLoginDTO
import com.ailibrary.org.dto.UserSaveDTO
import com.ailibrary.org.dto.UserUpdateDTO
import com.ailibrary.org.exception.InvalidCredentialsException
import com.ailibrary.org.exception.UserNotFoundByIdException
import com.ailibrary.org.exception.UserNotFoundException
import com.ailibrary.org.sharedDTOs.UserRespondDTO
import org.jetbrains.exposed.sql.and
import org.mindrot.jbcrypt.BCrypt

class UserRepository : UserContract {
    override suspend fun saveUser(userData: UserSaveDTO): UserRespondDTO = suspendTransaction {

        val hashedPassword: String = BCrypt.hashpw(userData.password, BCrypt.gensalt())

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
        val userDAO = UserDAO.find { UserTable.email eq email }.limit(1).firstOrNull()
            ?: throw UserNotFoundException(email)

        return@suspendTransaction UserRespondDTO(
            id = userDAO.id.value,
            firstName = userDAO.firstName,
            lastName = userDAO.lastName,
            email = userDAO.email
        )
    }

    override suspend fun updateUser(user: UserUpdateDTO): UserRespondDTO = suspendTransaction {
        val userDAO = UserDAO.findByIdAndUpdate(user.id) {
            it.email = user.email
            it.firstName = user.firstName
            it.lastName = user.lastName
        } ?: throw UserNotFoundByIdException(user.id)

        return@suspendTransaction UserRespondDTO(
            firstName = userDAO.firstName,
            lastName = userDAO.lastName,
            email = userDAO.email
        )

    }

    override suspend fun deleteUserById(user: UserDeleteDTO): UserRespondDTO = suspendTransaction {
        val userDAO = UserDAO.find { (UserTable.id eq user.id) and (UserTable.email eq user.email) }
            .firstOrNull() ?: throw UserNotFoundByIdException(user.id)

        userDAO.delete()

        return@suspendTransaction UserRespondDTO(
            firstName = userDAO.firstName,
            lastName = userDAO.lastName,
            email = userDAO.email
        )
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
            return@suspendTransaction UserRespondDTO(
                id = userDAO.id.value,
                firstName = userDAO.firstName,
                lastName = userDAO.lastName,
                email = userDAO.email
            )
        } else {
            throw InvalidCredentialsException()
        }
    }

    override suspend fun findUserById(id: Int): UserRespondDTO = suspendTransaction {
        val userDAO = UserDAO.findById(id)
            ?: throw UserNotFoundByIdException(id)

        return@suspendTransaction UserRespondDTO(
            firstName = userDAO.firstName,
            lastName = userDAO.lastName,
            email = userDAO.email
        )
    }

    override suspend fun getUserList(): List<UserRespondDTO> {
        return suspendTransaction {
            UserDAO.all().map { userDAO ->
                UserRespondDTO(
                    id = userDAO.id.value,
                    firstName = userDAO.firstName,
                    lastName = userDAO.lastName,
                    email = userDAO.email
                )
            }
        }
    }

    override suspend fun changeUserPassword(userChangePasswordDTO: UserChangePasswordDTO): UserRespondDTO =
        suspendTransaction {
            val userDAO = UserDAO.findById(userChangePasswordDTO.id)
                ?: throw UserNotFoundByIdException(userChangePasswordDTO.id)
            val hashedPassword: String = BCrypt.hashpw(userChangePasswordDTO.newPassword, BCrypt.gensalt())

            userDAO.passwordHash = hashedPassword

            return@suspendTransaction UserRespondDTO(
                id = userDAO.id.value,
                firstName = userDAO.firstName,
                lastName = userDAO.lastName,
                email = userDAO.email
            )
        }
}