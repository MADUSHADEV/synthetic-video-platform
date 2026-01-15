package com.ailibrary.org.routes.V1

import com.ailibrary.org.dto.UserChangePasswordDTO
import com.ailibrary.org.dto.UserDeleteDTO
import com.ailibrary.org.dto.UserLoginDTO
import com.ailibrary.org.dto.UserSaveDTO
import com.ailibrary.org.dto.UserUpdateDTO
import com.ailibrary.org.service.IdentityService
import com.ailibrary.org.validation.validateOrThrow
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.delete
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureIdentityRoutesV1() {


    // Inject the IdentityService using Koin
    val identityService: IdentityService by inject<IdentityService>()

    // Install the ContentNegotiation feature with JSON support
    install(ContentNegotiation) {
        json()
    }


    //TODO:
    // Validation : done
    // Send response same format
    // Token Generation


    // Configure the routing for the application
    routing {
        route("/api/v1/identity") {

            post("/register") {

                val userData = call.receive<UserSaveDTO>()

                userData.validateOrThrow()

                val result = identityService.SignUp(userData)
                call.respond(HttpStatusCode.OK, result)

            }

            post("/login") {

                val loginData = call.receive<UserLoginDTO>()

                loginData.validateOrThrow()

                val result = identityService.Login(loginData)
                call.respond(HttpStatusCode.OK, result)

            }

            put("/editUser") {

                val userData = call.receive<UserUpdateDTO>()
                val result = identityService.EditUser(userData)
                call.respond(HttpStatusCode.OK, result)

            }

            put("/changePassword") {


                val userData = call.receive<UserChangePasswordDTO>()

                userData.validateOrThrow()

                val result = identityService.ChangePassword(userData)
                call.respond(HttpStatusCode.OK, result)


            }

            delete("/deleteUser") {

                val userData = call.receive<UserDeleteDTO>()

                userData.validateOrThrow()

                val result = identityService.DeleteUser(userData)
                call.respond(HttpStatusCode.OK, result)

            }

        }
    }


}