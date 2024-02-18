package dev.h2r.domain.validator

import dev.h2r.domain.entity.Transaction
import java.math.BigInteger

object PerformTransactionValidator {
    fun validate(transaction: Transaction) {
        require(transaction.value > BigInteger.ZERO) { "Invalid transaction value" }
        require(
            transaction.description.isNotBlank()
                    && transaction.description.length > 1
                    && transaction.description.length < 10
        ) { "Invalid transaction description" }
    }
}
