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

object UserStatusTable : IntIdTable("user_status") {
    val name = varchar("name", 50).uniqueIndex()
}

class UserStatusDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserStatusDAO>(UserStatusTable)

    var name by UserStatusTable.name
}

object UserTable : IntIdTable("users") {
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val email = varchar("email", 100).uniqueIndex()
    val passwordHash = varchar("password_hash", 255)
    val statusId = reference("status_id", UserStatusTable).default(EntityID(1, UserStatusTable))
}

class UserDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserDAO>(UserTable)

    var firstName by UserTable.firstName
    var lastName by UserTable.lastName
    var email by UserTable.email
    var passwordHash by UserTable.passwordHash
    var status by UserStatusDAO referencedOn UserTable.statusId
}

suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)

fun daoToModel(dao: UserDAO) = User(
    id = dao.id.value,
    firstName = dao.firstName,
    lastName = dao.lastName,
    email = dao.email,
    passwordHash = dao.passwordHash,
    status = dao.status.name
)

fun daoToResponseDTO(dao: UserDAO) = UserRespondDTO(
    id = dao.id.value,
    firstName = dao.firstName,
    lastName = dao.lastName,
    email = dao.email,
    status = dao.status.name
)

fun daoToSaveDTO(dao: UserDAO) = UserSaveDTO(
    firstName = dao.firstName,
    lastName = dao.lastName,
    email = dao.email,
    password = dao.passwordHash
)