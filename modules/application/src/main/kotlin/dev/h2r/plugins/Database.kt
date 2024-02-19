package dev.h2r.plugins

import dev.h2r.infra.database.DatabaseSingleton
import io.ktor.server.application.*
import javax.xml.crypto.Data

fun Application.database() {
    val host = environment.config.property("ktor.database.host").getString()
    val port = environment.config.property("ktor.database.port").getString()
    val database = environment.config.property("ktor.database.database").getString()
    val user = environment.config.property("ktor.database.user").getString()
    val password = environment.config.property("ktor.database.password").getString()

    DatabaseSingleton.startConnection(host, port, database, user, password)

    environment.monitor.subscribe(ApplicationStopped) {
        log.info("Closing MongoClient")
        DatabaseSingleton.closeConnection()
    }
}