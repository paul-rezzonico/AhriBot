package com.paulrezzonico.repository

import com.paulrezzonico.entity.Status
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface StatusRepository : MongoRepository<Status, Int> {
    override fun findAll(): List<Status>
}
