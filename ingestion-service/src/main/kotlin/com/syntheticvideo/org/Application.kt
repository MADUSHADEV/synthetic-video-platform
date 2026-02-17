package com.syntheticvideo.org


import com.syntheticvideo.org.config.configureDatabases
import com.syntheticvideo.org.config.configureMinio
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureDatabases()
    configureMinio()
}

