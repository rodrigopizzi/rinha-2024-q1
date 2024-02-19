package dev.h2r.domain.entity

import java.time.LocalDateTime

data class Statement(
    val balance: Balance,
    val lastTransactions: List<Transaction>
)

data class Balance(
    val total: Int,
    val date: LocalDateTime,
    val limit: Int
)
