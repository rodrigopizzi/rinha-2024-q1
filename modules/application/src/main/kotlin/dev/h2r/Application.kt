package dev.h2r

import dev.h2r.plugins.database
import dev.h2r.plugins.dependencyInjection
import dev.h2r.plugins.exceptionHandler
import dev.h2r.plugins.routing
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*

fun main(args: Array<String>): Unit = EngineMain.main(args)

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }

    dependencyInjection()
    exceptionHandler()
    database()
    routing()
}