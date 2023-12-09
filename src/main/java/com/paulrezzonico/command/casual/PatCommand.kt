package com.paulrezzonico.command.casual

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component
import java.awt.Color

@Component
class PatCommand : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val embed = EmbedBuilder()
        if (event.name == "pat") {
            val userName = event.getOption("user")!!.asUser.asMention
            val gifUrl =
                    "https://media.tenor.com/mecnd_qE8p8AAAAd/anime-pat.gif"

            embed.setColor(Color.PINK)
            embed.setDescription("Patting $userName")
            embed.setImage(gifUrl)

            event.replyEmbeds(embed.build()).queue()
        }
    }
}
