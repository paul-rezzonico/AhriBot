package com.paulrezzonico.repository

import com.paulrezzonico.entity.VoiceLine
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface VoiceLinesRepository :  MongoRepository<VoiceLine, Int>{
    override fun findAll(): List<VoiceLine>
}
