package com.ailibrary.org.auth

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.jwt
import io.ktor.server.response.respond

/*
* Configure JWT authentication for the Ktor application.
**/
fun Application.configureSecurity() {

    // Load JWT configuration from application config or environment variables
    val jwtAudience = environment.config.propertyOrNull("jwt.audience")?.getString()
        ?: System.getenv("JWT_AUDIENCE")
        ?: "your-audience"
    val jwtDomain = environment.config.propertyOrNull("jwt.issuer")?.getString()
        ?: System.getenv("JWT_ISSUER")
        ?: "http://localhost:8080/"
    val jwtRealm = environment.config.propertyOrNull("jwt.realm")?.getString()
        ?: System.getenv("JWT_REALM")
        ?: "your-realm"
    val jwtSecret = environment.config.propertyOrNull("jwt.secret")?.getString()
        ?: System.getenv("JWT_SECRET")
        ?: "your-secret-key-change-in-production"

    install(Authentication) {
        jwt("auth-jwt") {
            realm = jwtRealm
            verifier(
                com.auth0.jwt.JWT
                    .require(com.auth0.jwt.algorithms.Algorithm.HMAC256(jwtSecret))
                    .withIssuer(jwtDomain)
                    .withAudience(jwtAudience)
                    .build()
            )
            validate { credential ->
                if (credential.payload.audience.contains(jwtAudience)) {
                    io.ktor.server.auth.jwt.JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(io.ktor.http.HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}