package com.paulrezzonico

import org.springframework.core.convert.converter.Converter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.core.convert.MongoCustomConversions
import java.util.UUID

@Configuration
open class MongoConfig {
    @Bean
    open fun customConversions(): MongoCustomConversions {
        val converters = listOf(
            UUIDToStringConverter(),
            StringToUUIDConverter()
        )
        return MongoCustomConversions(converters)
    }
}

class UUIDToStringConverter : Converter<UUID, String> {
    override fun convert(source: UUID): String = source.toString()
}

class StringToUUIDConverter : Converter<String, UUID> {
    override fun convert(source: String): UUID = UUID.fromString(source)
}
