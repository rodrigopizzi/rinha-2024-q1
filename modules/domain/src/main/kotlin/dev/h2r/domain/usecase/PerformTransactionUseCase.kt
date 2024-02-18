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
import java.math.BigInteger
import kotlin.math.log

class PerformTransactionUseCase(
    private val clientDatabasePort: ClientDatabasePort
) {
    fun execute(idClient: Int, transaction: Transaction): TransactionResult {
        PerformTransactionValidator.validate(transaction)

        repeat(10) {
            val client = clientDatabasePort.find(idClient)
                ?: throw NotFoundException("The client $idClient not found")

            val newBalance = when (transaction.type) {
                CREDIT -> client.balance.plus(transaction.value)
                DEBIT -> client.balance.subtract(transaction.value)
            }

            if (newBalance < BigInteger.ZERO && newBalance.abs() > client.limit)
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