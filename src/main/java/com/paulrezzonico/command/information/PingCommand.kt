package com.paulrezzonico.command.information

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component

@Component
class PingCommand : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (event.name == "ping") {
            val ping = event.jda.gatewayPing
            event.reply("Pong! 🏓\nLatency: ${ping}ms").queue()
        }
    }
}
