package com.paulrezzonico.repository

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.net.URL
import java.util.UUID

@Repository
interface PatGifRepository : MongoRepository<URL, UUID> {
    fun findByUrl(url: String): URL?
    fun deleteByUrl(url: String): Long
}