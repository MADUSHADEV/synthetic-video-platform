package com.ailibrary.org.routes.V1

import com.ailibrary.org.dto.UserChangePasswordDTO
import com.ailibrary.org.dto.UserDeleteDTO
import com.ailibrary.org.dto.UserLoginDTO
import com.ailibrary.org.dto.UserSaveDTO
import com.ailibrary.org.dto.UserUpdateDTO
import com.ailibrary.org.service.IdentityService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.get
import org.koin.ktor.ext.inject

fun Application.configureIdentityRoutesV1() {

    // Inject the IdentityService using Koin
    val identityService: IdentityService by inject<IdentityService>()

    // Install the ContentNegotiation feature with JSON support
    install(ContentNegotiation) {
        json()
    }


    //TODO:
    //Request
    // Resource
    // Validation
    // Token Generation

    // Configure the routing for the application
    routing {
        route("/api/v1/identity") {

            post("/register") {
                try {
                    val userData = call.receive<UserSaveDTO>()
                    val result = identityService.SignUp(userData)
                    call.respond(HttpStatusCode.OK, result)

                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to (e.message ?: "Unknown error")))
                }

            }

            post("/login") {
                try {
                    val loginData = call.receive<UserLoginDTO>()
                    val result = identityService.Login(loginData)
                    call.respond(HttpStatusCode.OK, result)

                } catch (e: Exception) {
                    call.respond(HttpStatusCode.Unauthorized, mapOf("error" to (e.message ?: "Unauthorized")))
                }
            }

            post("/editUser") {
                try {
                    val userData = call.receive<UserUpdateDTO>()
                    val result = identityService.EditUser(userData)
                    call.respond(HttpStatusCode.OK, result)

                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to (e.message ?: "Unknown error")))
                }
            }

            post("/changePassword") {
                try {

                    val userData = call.receive<UserChangePasswordDTO>()
                    val result = identityService.ChangePassword(userData)
                    call.respond(HttpStatusCode.OK, result)

                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to (e.message ?: "Unknown error")))
                }

            }

            delete("/deleteUser") {
                try {
                    val userData = call.receive<UserDeleteDTO>()
                    val result = identityService.DeleteUser(userData)
                    call.respond(HttpStatusCode.OK, result)

                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest, mapOf("error" to (e.message ?: "Unknown error")))
                }
            }

        }
    }


}