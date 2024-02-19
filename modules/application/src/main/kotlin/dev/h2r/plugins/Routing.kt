package dev.h2r.plugins

import dev.h2r.controller.ClientController
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.routing() {
    val clientController by inject<ClientController> ()

    this@routing.routing {
        post("/clientes/{id}/transacoes") {
            clientController.createTransaction(call)
        }
        get("/clientes/{id}/extrato") {
            clientController.statement(call)
        }
        get("/health") {
            call.respond(HttpStatusCode.OK,"Healthy")
        }
    }
}
