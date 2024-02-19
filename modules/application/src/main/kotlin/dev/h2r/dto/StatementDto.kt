package dev.h2r.dto

import dev.h2r.domain.entity.Balance
import dev.h2r.domain.entity.Statement
import dev.h2r.domain.entity.Transaction
import dev.h2r.dto.serializer.OffsetDateTimeSerializer
import dev.h2r.dto.serializer.TransactionTypeDtoSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.OffsetDateTime
import java.time.ZoneId

@Serializable
data class StatementDto(
    @SerialName("saldo")
    val balance: BalanceDto,

    @SerialName("ultimas_transacoes")
    val lastTransactions: List<TransactionDto>,
) {

    companion object {
        fun fromDomain(domain: Statement) = StatementDto(
            balance = BalanceDto.fromDomain(domain.balance),
            lastTransactions = domain.lastTransactions.map { TransactionDto.fromDomain(it) }
        )
    }

}

@Serializable
data class BalanceDto(
    @SerialName("total")
    val total: Int,

    @SerialName("data_extrato")
    @Serializable(with = OffsetDateTimeSerializer::class)
    val date: OffsetDateTime,

    @SerialName("limite")
    val limit: Int
) {
    companion object {
        fun fromDomain(domain: Balance) = BalanceDto(
            total = domain.total,
            date = domain.date.atZone(ZoneId.systemDefault()).toOffsetDateTime(),
            limit = domain.limit
        )
    }
}

@Serializable
data class TransactionDto(
    @SerialName("valor")
    val value: Int,

    @SerialName("tipo")
    @Serializable(with = TransactionTypeDtoSerializer::class)
    val type: TransactionTypeDto,

    @SerialName("descricao")
    val description: String,

    @SerialName("realizada_em")
    @Serializable(with = OffsetDateTimeSerializer::class)
    val createdAt: OffsetDateTime
) {
    companion object {
        fun fromDomain(domain: Transaction) = TransactionDto(
            value = domain.value,
            type = TransactionTypeDto.fromDomain(domain.type),
            description = domain.description,
            createdAt = domain.date.atZone(ZoneId.systemDefault()).toOffsetDateTime()
        )
    }
}