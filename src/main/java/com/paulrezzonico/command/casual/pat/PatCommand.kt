package com.paulrezzonico.command.casual.pat

import com.paulrezzonico.service.gif.PatGifService
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component
import java.awt.Color

@Component
class PatCommand(
    private val patGifService: PatGifService,
) : ListenerAdapter() {

    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        val gifUrl = patGifService.getRandomGifUrl()
        val embed = EmbedBuilder()
        if (event.name == "pat") {
            val userName = event.getOption("user")!!.asUser.asMention
            if (userName.isNotBlank()) {
                embed.setAuthor(event.user.asTag, null, event.user.effectiveAvatarUrl)
            } else {
                embed.setAuthor("", null, event.user.effectiveAvatarUrl)
            }
            embed.setColor(Color.PINK)
            embed.setDescription("**${event.user.asTag}** pats **$userName**")
            embed.setImage(gifUrl)

            event.replyEmbeds(embed.build()).queue()
        }
    }
}
