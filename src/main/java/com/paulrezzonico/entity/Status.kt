package com.paulrezzonico.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class Status(
    @Id
    val id: Int,
    val status: String
)
