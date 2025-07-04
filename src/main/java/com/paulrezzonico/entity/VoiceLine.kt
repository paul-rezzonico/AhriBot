package com.paulrezzonico.entity

import org.springframework.data.annotation.Id

data class VoiceLine(
    @Id
    val id: Int,
    val voiceLine: String,
)
