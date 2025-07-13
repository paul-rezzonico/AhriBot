package com.paulrezzonico.changelogs

import io.mongock.api.annotations.ChangeUnit
import io.mongock.api.annotations.Execution
import io.mongock.api.annotations.RollbackExecution
import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate

@ChangeUnit(id = "voice-lines-changelog", order = "002", author = "Paul REZZONICO")
class VoiceLinesChangelog {

    @Execution
    fun execution(mongoTemplate: MongoTemplate) {
        val collectionName = "voiceLines"
        if (!mongoTemplate.collectionExists(collectionName)) {
            mongoTemplate.createCollection(collectionName)
        }

        val voiceLinesCollection = mongoTemplate.getCollection("voiceLines")
        val voiceLinesData = listOf(
            "Do you not believe me?",
            "Playtime’s over.",
            "Will we?",
            "Embrace me.",
            "How seductive.",
            "Don’t be reluctant.",
            "Inform me of a secret.",
            "Do you not believe me?",
            "I am aware of their wants.",
            "Should I raise your heart rate? Or… stop! (giggle)",
            "If you’d like to play with me, you’d better be sure you know the game.",
            "Come try your luck, if you think you’re in my league. (giggle)",
            "Playtime’s over.",
            "They’re mine now.",
            "It’s too late for mercy.",
            "Let’s have some real fun.",
            "No one will stand in my way.",
            "They’ve exhausted their use.",
            "I'm Ahri, the Nine-Tailed Fox.",
            "Don't you trust me?",
            "Come play with me!",
            "I am not a monster.",
            "Don't worry, I'll be gentle...",
            "They won't even see me coming.",
            "I do enjoy a good chase.",
            "You were just a little too slow.",
            "Trust me... you won't like the consequences.",
            "I never miss.",
            "The chase is what I live for.",
            "I'm not a monster. I'm a... seductress.",
            "Who needs a fairy tale when you can have this?",
            "Why fight when you can just... enjoy the game?",
            "I make my own fate.",
            "In the end, we all dance with death.",
            "I will show you the meaning of seduction.",
            "Watch your step... or I'll make you mine.",
            "The soul is a precious thing... too bad you don't get to keep yours.",
            "You should see the other guy. He was delicious.",
            "Aren't you curious what I could do to you?",
        )
        voiceLinesData.forEachIndexed { index, voiceLine ->
            val document = Document("_id", index + 1).append("voiceLine", voiceLine)
            voiceLinesCollection.insertOne(document)
        }
    }

    @RollbackExecution
    fun rollbackExecution(mongoTemplate: MongoTemplate) {
        val collectionName = "voiceLines"
        if (mongoTemplate.collectionExists(collectionName)) {
            mongoTemplate.dropCollection(collectionName)
        }
    }
}
