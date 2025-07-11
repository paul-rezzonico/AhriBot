package com.paulrezzonico.service.quote

import com.paulrezzonico.repository.VoiceLinesRepository
import com.paulrezzonico.service.mongo.RandomMongoEntityService
import org.springframework.stereotype.Service

@Service
class VoiceLinesQuoteService(
    private val voiceLinesRepository: VoiceLinesRepository,
    private val randomMongoRepository: RandomMongoEntityService,
) : QuoteService {
    override fun getRandomQuote(): String {
        return randomMongoRepository.getRandomEntityFromRepository(voiceLinesRepository)
            ?.voiceLine ?: "No quotes available"
    }
}
