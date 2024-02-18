package dev.h2r.domain.entity

import java.math.BigInteger
import java.time.LocalDateTime

data class Transaction(
    val value: BigInteger,
    val type: TransactionType,
    val description: String,
    val date: LocalDateTime
)

enum class TransactionType(operation: String) {
    CREDIT("c"), DEBIT("d");
}
