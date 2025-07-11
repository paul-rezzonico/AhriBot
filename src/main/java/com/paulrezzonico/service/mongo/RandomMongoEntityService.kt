package com.paulrezzonico.service.mongo

import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class RandomMongoEntityService(
    private val mongoTemplate: MongoTemplate
) {
    fun <T> getRandomEntity(collectionName: String, entityClass: Class<T>): T? {
        val count = mongoTemplate.getCollection(collectionName).countDocuments()
        if (count == 0L) return null
        val skip = Random.nextLong(count)
        val result: List<T> = mongoTemplate.findAll(entityClass, collectionName)
        return if (result.isNotEmpty()) result[skip.toInt()] else null
    }
}
