package dev.h2r.domain.usecase

import dev.h2r.domain.entity.Transaction
import dev.h2r.domain.entity.TransactionResult
import dev.h2r.domain.entity.TransactionType.CREDIT
import dev.h2r.domain.entity.TransactionType.DEBIT
import dev.h2r.domain.exception.InsufficientFundsException
import dev.h2r.domain.exception.NotFoundException
import dev.h2r.domain.exception.RecordChangedByAnotherOperationException
import dev.h2r.domain.port.ClientDatabasePort
import dev.h2r.domain.validator.PerformTransactionValidator
import kotlin.math.absoluteValue

class PerformTransactionUseCase(
    private val clientDatabasePort: ClientDatabasePort
) {
    fun execute(idClient: Int, transaction: Transaction): TransactionResult {
        PerformTransactionValidator.validate(transaction)

        repeat(100) {
            val client = clientDatabasePort.find(idClient)
                ?: throw NotFoundException("The client $idClient not found")

            val newBalance = when (transaction.type) {
                CREDIT -> client.balance + transaction.value
                DEBIT -> client.balance - transaction.value
            }

            if (newBalance < 0 &&  newBalance.absoluteValue > client.limit)
                throw InsufficientFundsException("Insufficient funds")

            val clientChanged = clientDatabasePort.updateBalance(client, transaction, newBalance)
            if (clientChanged != null) {
                return TransactionResult(
                    limit = clientChanged.limit,
                    balance = clientChanged.balance
                )
            }
        }

        throw RecordChangedByAnotherOperationException("The client $idClient changed by another operation")
    }
}