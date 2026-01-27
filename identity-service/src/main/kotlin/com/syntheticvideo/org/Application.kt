package com.syntheticvideo.org


import com.syntheticvideo.org.auth.configureSecurity
import com.syntheticvideo.org.config.configureDatabases
import com.syntheticvideo.org.di.applyIdentityModule
import com.syntheticvideo.org.routes.V1.configureIdentityRoutesV1
import com.syntheticvideo.org.utils.configureRespondExceptionHandler
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {

    install(Koin) {
        slf4jLogger()
        modules(applyIdentityModule)
    }
    configureSecurity()
    configureDatabases()
    configureRespondExceptionHandler()
    configureIdentityRoutesV1()
}



