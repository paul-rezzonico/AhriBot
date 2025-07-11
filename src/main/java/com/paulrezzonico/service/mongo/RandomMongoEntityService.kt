package com.paulrezzonico.service.mongo

import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class RandomMongoEntityService {
    // TODO: Find a way to avoid loading all entities into memory
    fun <T, ID> getRandomEntityFromRepository(repository: MongoRepository<T, ID>): T? {
        val all: List<T> = repository.findAll()
        if (all.isEmpty()) return null
        return all[Random.nextInt(all.size)]
    }
}
