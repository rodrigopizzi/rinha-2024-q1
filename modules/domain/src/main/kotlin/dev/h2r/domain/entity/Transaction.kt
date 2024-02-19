package dev.h2r.domain.entity

import java.time.LocalDateTime

data class Transaction(
    val value: Int,
    val type: TransactionType,
    val description: String,
    val date: LocalDateTime
)

enum class TransactionType {
    CREDIT, DEBIT;
}
