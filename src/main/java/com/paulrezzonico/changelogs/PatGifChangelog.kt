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
            "https://media1.tenor.com/images/af76e9a0652575b414251b6490509a36/tenor.gif",
            "https://media1.tenor.com/images/ba551e0f5a51c7d77a3ca4e62a14a919/tenor.gif",
            "https://media1.tenor.com/images/bb5608910848ba61808c8f28caf6ec7d/tenor.gif",
            "https://media1.tenor.com/images/83eee1df66a791ce6057fc80c026f11a/tenor.gif",
            "https://media1.tenor.com/images/54722063c802bac30d928db3bf3cc3b4/tenor.gif",
            "https://media1.tenor.com/images/153e9bdd80008e8c0f94110450fcbf98/tenor.gif",
            "https://media1.tenor.com/images/116fe7ede5b7976920fac3bf8067d42b/tenor.gif",
            "https://media1.tenor.com/images/c0bcaeaa785a6bdf1fae82ecac65d0cc/tenor.gif",
            "https://media1.tenor.com/images/1e92c03121c0bd6688d17eef8d275ea7/tenor.gif",
            "https://media1.tenor.com/images/28f4f29de42f03f66fb17c5621e7bedf/tenor.gif",
            "https://media1.tenor.com/images/005e8df693c0f59e442b0bf95c22d0f5/tenor.gif",
            "https://media1.tenor.com/images/291ea37382e1d6cd33349c50a398b6b9/tenor.gif",
            "https://media1.tenor.com/images/3e94b77bfc7d4e240bb530b347a84008/tenor.gif",
            "https://media1.tenor.com/images/e71e45362fccc0b9a2ccce97bff93780/tenor.gif",
            "https://media1.tenor.com/images/8c1f6874db27c8227755a08b2b07740b/tenor.gif",
            "https://media1.tenor.com/images/43ad0cfc99f22dbbf3fa9dacf31d8599/tenor.gif",
            "https://media1.tenor.com/images/70960e87fb9454df6a1d15c96c9ad955/tenor.gif",
            "https://media1.tenor.com/images/2e62cd7491be4ec9f0ec210d648b80fd/tenor.gif",
            "https://media1.tenor.com/images/9fa1e50a657ea2ece043de6e0e93ac8e/tenor.gif",
            "https://media1.tenor.com/images/da2e7e096c87fbb0ac422944c4941337/tenor.gif",
            "https://media1.tenor.com/images/0817c784ab4e923e8cafb05263876f0a/tenor.gif",
            "https://media1.tenor.com/images/ae7a21f434cee8762d127b8aed889296/tenor.gif",
            "https://media1.tenor.com/images/bf646b7164b76efe82502993ee530c78/tenor.gif",
            "https://media1.tenor.com/images/398c9c832335a13be124914c23e88fdf/tenor.gif",
            "https://media1.tenor.com/images/a6392ee39e9a419b33fa44eb5af7cade/tenor.gif",
            "https://media1.tenor.com/images/d9e575861bb2f6389cec93da6cbdfa1f/tenor.gif",
            "https://media1.tenor.com/images/1291995032c16083bfeaac37545e2f4e/tenor.gif",
            "https://media1.tenor.com/images/6691256deb7937e5c98b26bdbbd7bbfa/tenor.gif",
            "https://media1.tenor.com/images/41b38c14a4e74c6a24b4c3952e9e0e8d/tenor.gif",
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
