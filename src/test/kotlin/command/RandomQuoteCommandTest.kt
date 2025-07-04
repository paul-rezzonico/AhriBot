package command

import com.paulrezzonico.command.ahri.quote.QuoteService
import com.paulrezzonico.command.ahri.quote.RandomQuoteCommand
import com.paulrezzonico.util.QuoteManager
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class RandomQuoteCommandTest {

    private lateinit var randomQuoteCommand: RandomQuoteCommand
    private lateinit var quoteService : QuoteService
    private lateinit var event: SlashCommandInteractionEvent

    @BeforeEach
    fun setup() {
        quoteService = mock(QuoteService::class.java)
        randomQuoteCommand = RandomQuoteCommand(quoteService)
        event = mock(SlashCommandInteractionEvent::class.java)
    }

    @Test
    fun testOnSlashCommandInteraction() {
        `when`(event.name).thenReturn("quote")
        `when`(quoteService.getRandomQuote()).thenReturn("Test quote")
        val replyAction = mock(ReplyCallbackAction::class.java)
        `when`(event.reply("*Test quote*")).thenReturn(replyAction)

        randomQuoteCommand.onSlashCommandInteraction(event)

        Mockito.verify(event).reply("*Test quote*")
        Mockito.verify(replyAction).queue()
    }
}
