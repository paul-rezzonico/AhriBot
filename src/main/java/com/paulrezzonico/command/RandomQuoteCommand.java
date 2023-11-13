package com.paulrezzonico.command;

import com.paulrezzonico.util.QuoteManager;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RandomQuoteCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMessage().getContentRaw().equals("!randomquote")) {
            QuoteManager quoteManager = new QuoteManager();
            String quote = quoteManager.getRandomQuote();
            event.getChannel().sendMessage(quote).queue();
        }
    }
}
