package com.paulrezzonico.service.gif

import com.paulrezzonico.repository.PatGifRepository
import com.paulrezzonico.service.mongo.RandomMongoEntityService
import org.springframework.stereotype.Service

private const val DEFAULT_PAT_GIF = "https://media.tenor.com/mecnd_qE8p8AAAAd/anime-pat.gif"

@Service
class PatGifService(
    private val patGifRepository: PatGifRepository,
    private val randomMongoEntityService: RandomMongoEntityService
) {
    fun getRandomGifUrl(): String {
        return randomMongoEntityService.getRandomEntityFromRepository(patGifRepository)
            ?.url ?: DEFAULT_PAT_GIF
    }
}
