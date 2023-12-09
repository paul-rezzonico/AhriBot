package com.paulrezzonico.command.casual;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.util.Objects;

@Component
public class PatCommand extends ListenerAdapter {

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        EmbedBuilder embed = new EmbedBuilder();
        if (event.getName().equals("pat")) {
            String userName = Objects.requireNonNull(event.getOption("user")).getAsUser().getAsMention();
            String gifUrl =
                    "https://media.tenor.com/mecnd_qE8p8AAAAd/anime-pat.gif";

            embed.setColor(Color.PINK);
            embed.setDescription("Patting " + userName);
            embed.setImage(gifUrl);

            event.replyEmbeds(embed.build()).queue();
        }
    }
}
