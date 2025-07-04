package com.paulrezzonico.command.ahri.quote

import com.paulrezzonico.repository.VoiceLinesRepository
import org.springframework.stereotype.Service

@Service
class VoiceLinesQuoteService(
    private val voiceLinesRepository: VoiceLinesRepository
) : QuoteService {
    override fun getRandomQuote(): String {
        return voiceLinesRepository.findAll()
            .shuffled()
            .firstOrNull()?.voiceLine ?: "No quotes available."
    }
}
