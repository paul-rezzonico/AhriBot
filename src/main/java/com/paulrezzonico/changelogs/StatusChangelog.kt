package com.paulrezzonico.changelogs

import io.mongock.api.annotations.ChangeUnit
import io.mongock.api.annotations.Execution
import io.mongock.api.annotations.RollbackExecution
import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate

@ChangeUnit(id = "status-changelog", order = "001", author = "Paul REZZONICO")
class StatusChangelog {

    @Execution
    fun execution(mongoTemplate: MongoTemplate) {
        val collectionName = "status"
        if (!mongoTemplate.collectionExists(collectionName)) {
            mongoTemplate.createCollection(collectionName)
        }

        val statusCollection = mongoTemplate.getCollection("status")
        val statusData = listOf(
            "Charming hearts on Discord!",
            "Exploring mystical paths...",
            "Summoning spirits on the server!",
            "Playing with emotions...",
            "Navigating through the stars.",
            "Follow the nine-tailed fox.",
            "Guiding lost souls.",
            "Dancing with spirits.",
            "Weaving magical illusions.",
            "Seeking adventures in Ionia.",
            "Sharing wisdom from Ionia.",
            "Meditating on the mysteries of the universe.",
            "Paying homage to the Moon.",
            "Journeying through the spirit realm.",
            "Captivating hearts with melodies."
        )
        statusData.forEachIndexed { index, status ->
            val document = Document("_id", index + 1).append("status", status)
            statusCollection.insertOne(document)
        }
    }

    @RollbackExecution
    fun rollbackExecution(mongoTemplate: MongoTemplate) {
        val collectionName = "status"
        if (mongoTemplate.collectionExists(collectionName)) {
            mongoTemplate.dropCollection(collectionName)
        }
    }
}
