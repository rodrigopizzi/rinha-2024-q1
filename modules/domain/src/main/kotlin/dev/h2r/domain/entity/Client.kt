package dev.h2r.domain.entity

import java.math.BigInteger

data class Client(
    val id: Int,
    val balance: BigInteger,
    val limit: BigInteger,
    val last10Transactions: List<Transaction>
)