package com.ailibrary.org.db

import com.ailibrary.org.dto.UserSaveDTO
import com.ailibrary.org.model.User
import com.ailibrary.org.sharedDTOs.UserRespondDTO
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object UserTable : IntIdTable("users") {
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val email = varchar("email", 100).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
}

class UserDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDAO>(UserTable)

    var firstName by UserTable.firstName
    var lastName by UserTable.lastName
    var email by UserTable.email
    var passwordHash by UserTable.passwordHash
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: UserDAO) = User(
    id = dao.id.value,
    firstName = dao.firstName,
    lastName = dao.lastName,
    email = dao.email,
    passwordHash = dao.passwordHash
)

fun daoToResponseDTO(dao: UserDAO) = UserRespondDTO(
    id = dao.id.value,
    firstName = dao.firstName,
    lastName = dao.lastName,
    email = dao.email
)

fun daoToSaveDTO(dao: UserDAO) = UserSaveDTO(
    firstName = dao.firstName,
    lastName = dao.lastName,
    email = dao.email,
    passwordHash = dao.passwordHash
)