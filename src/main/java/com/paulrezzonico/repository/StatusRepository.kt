package com.paulrezzonico.repository

import com.paulrezzonico.entity.Status
import org.springframework.data.mongodb.repository.MongoRepository

interface StatusRepository : MongoRepository<Status, Int> {
    override fun findAll(): List<Status>
}