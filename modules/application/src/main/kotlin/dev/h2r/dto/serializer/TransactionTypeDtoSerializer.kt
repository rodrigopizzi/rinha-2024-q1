package dev.h2r.dto.serializer

import dev.h2r.dto.TransactionTypeDto
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = TransactionTypeDto::class)
object TransactionTypeDtoSerializer : KSerializer<TransactionTypeDto> {
    override fun serialize(encoder: Encoder, value: TransactionTypeDto) {
        encoder.encodeString(value.name.lowercase())
    }

    override fun deserialize(decoder: Decoder): TransactionTypeDto {
        return TransactionTypeDto.valueOf(decoder.decodeString().uppercase())
    }
}