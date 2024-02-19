package dev.h2r.dto

import dev.h2r.domain.entity.Transaction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class TransactionRequestDto(
    @SerialName("valor")
    val value: Int,

    @SerialName("tipo")
    val type: TransactionTypeDto,

    @SerialName("descricao")
    val description: String
) {
    fun toDomain() = Transaction(
        value = value,
        type = type.toDomain(),
        description = description,
        date = LocalDateTime.now()
    )
}


