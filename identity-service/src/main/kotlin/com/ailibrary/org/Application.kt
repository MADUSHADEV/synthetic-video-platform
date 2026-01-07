package com.ailibrary.org

import com.ailibrary.org.auth.configureSecurity
import com.ailibrary.org.config.configureDatabases
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSecurity()
    configureDatabases()
}


