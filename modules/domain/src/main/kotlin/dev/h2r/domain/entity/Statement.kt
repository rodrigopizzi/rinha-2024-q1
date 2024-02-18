package dev.h2r.domain.entity

import java.math.BigInteger
import java.time.LocalDateTime

data class Statement(
    val balance: Balance,
    val lastTransactions: List<Transaction>
)

data class Balance(
    val total: BigInteger,
    val date: LocalDateTime,
    val limit: BigInteger
)
