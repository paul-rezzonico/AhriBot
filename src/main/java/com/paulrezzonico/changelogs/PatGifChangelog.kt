package com.paulrezzonico.changelogs

import io.mongock.api.annotations.ChangeUnit
import io.mongock.api.annotations.Execution
import io.mongock.api.annotations.RollbackExecution
import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate

@ChangeUnit(id = "pat-gif-changelog", order = "003", author = "Paul REZZONICO")
class PatGifChangelog {
    @Execution
    fun execution(mongoTemplate: MongoTemplate) {
        val collectionName = "pat_gifs"
        if (!mongoTemplate.collectionExists(collectionName)) {
            mongoTemplate.createCollection(collectionName)
        }

        val patGifCollection = mongoTemplate.getCollection("pat_gifs")
        val patGifsData = listOf(
            "https://example.com/pat_gif_1.gif",
            // TODO add actual URLs for pat gifs
        )
        patGifsData.forEachIndexed { index, url ->
            val document = Document("_id", index + 1).append("url", url)
            patGifCollection.insertOne(document)
        }
    }

    @RollbackExecution
    fun rollbackExecution(mongoTemplate: MongoTemplate) {
        val collectionName = "pat_gifs"
        if (mongoTemplate.collectionExists(collectionName)) {
            mongoTemplate.dropCollection(collectionName)
        }
    }
}
