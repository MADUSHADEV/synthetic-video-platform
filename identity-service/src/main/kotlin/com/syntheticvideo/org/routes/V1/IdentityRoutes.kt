package com.syntheticvideo.org.routes.V1

import com.syntheticvideo.org.dto.UserChangePasswordDTO
import com.syntheticvideo.org.dto.UserDeleteDTO
import com.syntheticvideo.org.dto.UserStatusChangeDTO
import com.syntheticvideo.org.dto.UserLoginDTO
import com.syntheticvideo.org.dto.UserSaveDTO
import com.syntheticvideo.org.dto.UserUpdateDTO
import com.syntheticvideo.org.protocol.ResponseProtocol
import com.syntheticvideo.org.service.IdentityService
import com.syntheticvideo.org.validation.PasswordRegex
import com.syntheticvideo.org.validation.validateOrThrow
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.auth.authenticate
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
    // Send response same format : done
    // Token Generation : done
    // Exception Handling : done
    // Validate profile update data : done
    // Learn Koin DSL for better DI management : to be done

    // Configure the routing for the application
    routing {
        route("/api/v1/identity") {

            post("/register") {

                val userData = call.receive<UserSaveDTO>()

                userData.validateOrThrow()

                val result = identityService.SignUp(userData)
                call.respond(
                    HttpStatusCode.Created,
                    ResponseProtocol.success(
                        message = "User registered successfully",
                        data = result,
                        code = HttpStatusCode.Created.value
                    )
                )

            }

            post("/login") {

                val loginData = call.receive<UserLoginDTO>()

                loginData.validateOrThrow()

                val result = identityService.Login(loginData)
                call.respond(
                    HttpStatusCode.OK,
                    ResponseProtocol.success(
                        message = "User logged in successfully",
                        data = result,
                        code = HttpStatusCode.OK.value
                    )
                )

            }
            authenticate("auth-jwt") {

                put("/editUser") {

                    val userData = call.receive<UserUpdateDTO>()
                    val result = identityService.EditUser(userData)
                    call.respond(
                        HttpStatusCode.OK,
                        ResponseProtocol.success(
                            message = "User updated successfully",
                            data = result,
                            code = HttpStatusCode.OK.value
                        )
                    )

                }

                put("/changePassword") {

                    val userData = call.receive<UserChangePasswordDTO>()

                    userData.validateOrThrow()

                    val result = identityService.ChangePassword(userData)
                    call.respond(
                        HttpStatusCode.OK,
                        ResponseProtocol.success(
                            message = "Password changed successfully",
                            data = result,
                            code = HttpStatusCode.OK.value
                        )
                    )

                }

                put("/logout") {

                    val userData = call.receive<UserStatusChangeDTO>()

                    userData.validateOrThrow()

                    val result = identityService.Logout(userData)

                    call.respond(
                        HttpStatusCode.OK,
                        ResponseProtocol.success(
                            message = "User logged out successfully",
                            data = result,
                            code = HttpStatusCode.OK.value
                        )
                    )

                }
            }

            delete("/deleteUser") {

                val userData = call.receive<UserDeleteDTO>()

                userData.validateOrThrow()

                val result = identityService.DeleteUser(userData)
                call.respond(
                    HttpStatusCode.OK,
                    ResponseProtocol.success(
                        message = "User deleted successfully",
                        data = result,
                        code = HttpStatusCode.OK.value
                    )
                )

            }

        }
    }


}
