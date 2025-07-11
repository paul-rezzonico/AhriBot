package com.paulrezzonico.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.UUID

@Document(collection = "pat_gifs")
data class PatGif(
    @Id
    val id: UUID = UUID.randomUUID(),
    val url: String,
)
