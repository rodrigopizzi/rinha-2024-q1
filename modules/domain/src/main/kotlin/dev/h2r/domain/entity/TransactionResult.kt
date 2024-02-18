package dev.h2r.domain.entity

import java.math.BigInteger

data class TransactionResult(
    val limit: BigInteger,
    val balance: BigInteger
)
