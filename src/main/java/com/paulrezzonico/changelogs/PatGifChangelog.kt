package com.paulrezzonico.changelogs

import io.mongock.api.annotations.ChangeUnit
import io.mongock.api.annotations.Execution
import io.mongock.api.annotations.RollbackExecution
import org.bson.Document
import org.springframework.data.mongodb.core.MongoTemplate
import java.util.*

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
            "https://c.tenor.com/RWAVN9L5yzYAAAAC/tenor.gif",
            "https://c.tenor.com/afKzI9a28lIAAAAC/tenor.gif",
            "https://c.tenor.com/juz5uR4pJeEAAAAC/tenor.gif",
            "https://c.tenor.com/sECU58-YCJIAAAAC/tenor.gif",
            "https://c.tenor.com/5dezHjhjBucAAAAC/tenor.gif",
            "https://c.tenor.com/ynFUoAowiP4AAAAC/tenor.gif",
            "https://c.tenor.com/rZRQ6gSf128AAAAC/tenor.gif",
            "https://c.tenor.com/AnxesEqY2RwAAAAC/tenor.gif",
            "https://c.tenor.com/Av63tpT8Y14AAAAC/tenor.gif",
            "https://c.tenor.com/6K7c9liCWl8AAAAC/tenor.gif",
            "https://c.tenor.com/dLdNYQrLsp4AAAAC/tenor.gif",
            "https://c.tenor.com/OUSrLXimAq8AAAAC/tenor.gif",
            "https://c.tenor.com/6RnGiCzfPEUAAAAC/tenor.gif",
            "https://c.tenor.com/VD0XVCkKc_cAAAAC/tenor.gif",
            "https://c.tenor.com/HmAeAigba4UAAAAC/tenor.gif",
            "https://c.tenor.com/FG0h52Ga-I8AAAAC/tenor.gif",
            "https://c.tenor.com/JBC-2GUXwSwAAAAC/tenor.gif",
            "https://c.tenor.com/ixtSH1LN5Z8AAAAC/tenor.gif",
            "https://c.tenor.com/G3WsQADueVEAAAAC/tenor.gif",
            "https://c.tenor.com/UlH5kp11Ou8AAAAC/tenor.gif",
            "https://c.tenor.com/410EgaVSZbIAAAAC/tenor.gif",
            "https://c.tenor.com/ANnMiLporKMAAAAC/tenor.gif",
            "https://c.tenor.com/oRS4NId5gOEAAAAC/tenor.gif",
            "https://c.tenor.com/8w4TYd2tsKcAAAAC/tenor.gif",
            "https://c.tenor.com/fA9Zxa_bzREAAAAC/tenor.gif",
            "https://c.tenor.com/n5I44g_IXx4AAAAC/tenor.gif",
            "https://c.tenor.com/7V_j-EkvLbYAAAAC/tenor.gif",
            "https://c.tenor.com/JdSWUizBh_4AAAAC/tenor.gif",
            "https://c.tenor.com/t9VnkE7P1U8AAAAC/tenor.gif",
        )
        patGifsData.forEach { url ->
            val document = Document("_id", UUID.randomUUID()).append("url", url)
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
