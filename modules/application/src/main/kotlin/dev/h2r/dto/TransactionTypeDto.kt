package dev.h2r.dto

import dev.h2r.domain.entity.TransactionType
import dev.h2r.dto.serializer.TransactionTypeDtoSerializer
import kotlinx.serialization.Serializable

@Serializable(with = TransactionTypeDtoSerializer::class)
enum class TransactionTypeDto {
    C, D;

    fun toDomain(): TransactionType {
        return when (this) {
            C -> TransactionType.CREDIT
            D -> TransactionType.DEBIT
        }
    }

    companion object {
        fun fromDomain(domain: TransactionType) = when(domain) {
            TransactionType.CREDIT -> C
            TransactionType.DEBIT -> D
        }
    }
}

