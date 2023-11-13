package com.paulrezzonico.command;

import com.paulrezzonico.util.QuoteManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RandomQuoteCommand extends ListenerAdapter {

    @Autowired
    private QuoteManager quoteManager;
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("quote")) {
            String quote = quoteManager.getRandomQuote();
            event.reply("*" + quote + "*").queue();
        }
    }
}
