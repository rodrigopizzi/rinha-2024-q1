package dev.h2r.controller

import dev.h2r.domain.usecase.PerformTransactionUseCase
import dev.h2r.domain.usecase.StatementUseCase
import dev.h2r.dto.StatementDto
import dev.h2r.dto.TransactionRequestDto
import dev.h2r.dto.TransactionResponseDto
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*

class ClientController(
    private val performTransactionUseCae: PerformTransactionUseCase,
    private val statementUseCase: StatementUseCase
) {
    suspend fun createTransaction(call: ApplicationCall) {
        val idClient = Integer.parseInt(call.parameters["id"])
        val transaction = call.receive<TransactionRequestDto>().toDomain()

        val response = performTransactionUseCae.execute(idClient, transaction).let {
            TransactionResponseDto.fromDomain(it)
        }

        call.respond(response)
    }

    suspend fun statement(call: ApplicationCall) {
        val idClient = Integer.parseInt(call.parameters["id"])
        val response = statementUseCase.execute(idClient).let {
            StatementDto.fromDomain(it)
        }

        call.respond(response)
    }
}