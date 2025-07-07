package unit.command.ahri.quote

import com.paulrezzonico.command.ahri.quote.QuoteService
import com.paulrezzonico.command.ahri.quote.RandomQuoteCommand
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`

class RandomQuoteCommandTest {

    private lateinit var quoteService: QuoteService
    private lateinit var randomQuoteCommand: RandomQuoteCommand
    private lateinit var event: SlashCommandInteractionEvent
    private lateinit var replyAction: ReplyCallbackAction

    @BeforeEach
    fun setUp() {
        // Création des mocks
        quoteService = mock(QuoteService::class.java)
        event = mock()
        replyAction = mock()

        // Initialisation de la commande avec le service mocké
        randomQuoteCommand = RandomQuoteCommand(quoteService)
    }

    @Test
    fun `test quote command returns expected quote`() {
        // Arrange
        val expectedQuote = "Je suis Ahri !"
        `when`(quoteService.getRandomQuote()).thenReturn(expectedQuote)
        `when`(event.name).thenReturn("quote")
        `when`(event.reply(ArgumentMatchers.any<String>())).thenReturn(replyAction)

        // Act
        randomQuoteCommand.onSlashCommandInteraction(event)

        // Assert
        verify(event).reply("*$expectedQuote*")
        verify(replyAction).queue()
    }

    @Test
    fun `test quote command when no quotes available`() {
        // Arrange
        val noQuoteMessage = "No quotes available."
        `when`(quoteService.getRandomQuote()).thenReturn(noQuoteMessage)
        `when`(event.name).thenReturn("quote")
        `when`(event.reply(ArgumentMatchers.any<String>())).thenReturn(replyAction)

        // Act
        randomQuoteCommand.onSlashCommandInteraction(event)

        // Assert
        verify(event).reply("*$noQuoteMessage*")
        verify(replyAction).queue()
    }

    @Test
    fun `test command ignores non-quote commands`() {
        // Arrange
        `when`(event.name).thenReturn("other-command")

        // Act
        randomQuoteCommand.onSlashCommandInteraction(event)

        // Assert
        verify(quoteService, never()).getRandomQuote()
        verify(event, never()).reply(ArgumentMatchers.any<String>())
    }
}
