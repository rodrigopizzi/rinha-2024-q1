package dev.h2r.domain.usecase

import dev.h2r.domain.entity.Balance
import dev.h2r.domain.entity.Statement
import dev.h2r.domain.exception.NotFoundException
import dev.h2r.domain.port.ClientDatabasePort
import java.time.LocalDateTime

class StatementUseCase(
    private val clientDatabasePort: ClientDatabasePort
) {
    fun execute(idClient: Int): Statement {
        val client = clientDatabasePort.find(idClient)
            ?: throw NotFoundException("Client $idClient not found")

        return Statement(
            balance = Balance(
                total = client.balance,
                date = LocalDateTime.now(),
                limit = client.limit
            ),
            lastTransactions = client.last10Transactions
        )
    }
}