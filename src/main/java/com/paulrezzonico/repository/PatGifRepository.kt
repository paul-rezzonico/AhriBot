package com.paulrezzonico.repository

import com.paulrezzonico.entity.PatGif
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface PatGifRepository : MongoRepository<PatGif, UUID> {
    override fun findAll(): List<PatGif>
}
