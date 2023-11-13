package com.paulrezzonico.command;

import com.paulrezzonico.facade.IQuoteManagerFacade;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RandomQuoteCommand extends ListenerAdapter {

    @Autowired
    private IQuoteManagerFacade quoteManager;
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        if (event.getName().equals("randomquote")) {
            String quote = quoteManager.getRandomQuote();
            event.reply(quote).queue();
        }
    }
}
