package integration.command.ahri.quote

import com.paulrezzonico.command.ahri.quote.VoiceLinesQuoteService
import com.paulrezzonico.entity.VoiceLine
import com.paulrezzonico.repository.VoiceLinesRepository
import integration.AbstractIntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import kotlin.test.assertTrue

class VoiceLinesQuoteServiceIntegrationTest : AbstractIntegrationTest() {

    @Autowired
    private lateinit var voiceLinesRepository: VoiceLinesRepository

    @Autowired
    private lateinit var voiceLinesQuoteService: VoiceLinesQuoteService

    @Test
    fun `test getRandomQuote returns a migrated quote`() {
        val allQuotes = voiceLinesRepository.findAll().map { it.voiceLine }

        val quote = voiceLinesQuoteService.getRandomQuote()

        assertTrue(quote in allQuotes, "La citation retournée n'est pas issue de la migration")
    }
}
