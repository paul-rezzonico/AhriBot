package com.paulrezzonico.command.ahri.quote

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component

@Component
class RandomQuoteCommand(
    private val quoteService: QuoteService
) : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name == "quote") {
            val quote = quoteService.getRandomQuote()
            event.reply("*$quote*").queue()
        }
    }
}