package com.syntheticvideo.org


import com.syntheticvideo.org.config.configureDatabases
import io.ktor.server.application.Application

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureDatabases()
}

