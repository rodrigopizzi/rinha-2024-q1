package dev.h2r.dto

import dev.h2r.domain.entity.TransactionResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TransactionResponseDto(
    @SerialName("limite")
    val limit: Int,

    @SerialName("saldo")
    val balance: Int
) {
    companion object {
        fun fromDomain(domain: TransactionResult) = TransactionResponseDto(
            limit = domain.limit,
            balance = domain.balance
        )
    }
}