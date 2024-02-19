package dev.h2r.domain.validator

import dev.h2r.domain.entity.Transaction

object PerformTransactionValidator {
    fun validate(transaction: Transaction) {
        require(transaction.value > 0) { "Invalid transaction value ${transaction.value}" }
        require(isValidDescription(transaction.description)) {
            "Invalid transaction description ${transaction.description}"
        }
    }

    private fun isValidDescription(description: String) =
        description.isNotBlank() && description.length <= 10

}