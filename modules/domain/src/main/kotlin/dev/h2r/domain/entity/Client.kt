package dev.h2r.domain.entity


data class Client(
    val id: Int,
    val balance: Int,
    val limit: Int,
    val last10Transactions: List<Transaction>
)