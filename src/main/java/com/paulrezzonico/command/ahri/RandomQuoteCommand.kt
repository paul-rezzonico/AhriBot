package com.paulrezzonico.command.ahri

import com.paulrezzonico.util.QuoteManager
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class RandomQuoteCommand : ListenerAdapter() {
    @Autowired
    private val quoteManager: QuoteManager? = null

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name == "quote") {
            val quote = quoteManager!!.randomQuote
            event.reply("*$quote*").queue()
        }
    }
}
