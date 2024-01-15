import com.paulrezzonico.command.ahri.RandomQuoteCommand
import com.paulrezzonico.util.QuoteManager
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class RandomQuoteCommandTest {

    private lateinit var randomQuoteCommand: RandomQuoteCommand
    private lateinit var quoteManager: QuoteManager
    private lateinit var event: SlashCommandInteractionEvent

    @BeforeEach
    fun setup() {
        quoteManager = mock(QuoteManager::class.java)
        randomQuoteCommand = RandomQuoteCommand()
        event = mock(SlashCommandInteractionEvent::class.java)
    }

    @Test
    fun testOnSlashCommandInteraction() {
        `when`(event.name).thenReturn("quote")
        `when`(quoteManager.randomQuote).thenReturn("Test quote")
        randomQuoteCommand.onSlashCommandInteraction(event)
        Mockito.verify(event).reply("*Test quote*")
    }
}