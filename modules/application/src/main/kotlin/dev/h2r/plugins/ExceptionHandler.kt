package dev.h2r.plugins

import dev.h2r.domain.exception.InsufficientFundsException
import dev.h2r.domain.exception.NotFoundException
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.exceptionHandler() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            cause.printStackTrace()
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
        exception<InsufficientFundsException> { call, cause ->
            call.respondText(text = "422: $cause", status = HttpStatusCode.UnprocessableEntity)
        }
        exception<NotFoundException> { call, cause ->
            call.respondText(text = "404: $cause", status = HttpStatusCode.NotFound)
        }
        exception<IllegalArgumentException> { call, cause ->
            call.respondText(text = "422: $cause", status = HttpStatusCode.UnprocessableEntity)
        }
        exception<BadRequestException> { call, cause ->
            when (cause.cause) {
                is JsonConvertException -> call.respondText(text = "422: $cause", status = HttpStatusCode.UnprocessableEntity)
                else -> call.respondText(text = "400: $cause", status = HttpStatusCode.BadRequest)
            }
        }
    }
}