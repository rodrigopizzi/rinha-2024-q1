package dev.h2r.dto.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = LocalDateTime::class)
object OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {

    private val format = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        encoder.encodeString(value.format(format))
    }

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        return OffsetDateTime.from(format.parse(decoder.decodeString()))
    }
}