package com.ailibrary.org

import com.ailibrary.org.auth.configureSecurity
import com.ailibrary.org.config.configureDatabases
import com.ailibrary.org.di.applyIdentityModule
import com.ailibrary.org.routes.V1.configureIdentityRoutesV1
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
    configureIdentityRoutesV1()
}


