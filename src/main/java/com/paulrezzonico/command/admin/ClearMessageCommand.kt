package com.paulrezzonico.command.admin

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter

class ClearMessageCommand : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (AdminPermissionUtils.isNotAdmin(event)) return
        if (event.name != "clear") return
        handleClearCommand(event)
    }

    private fun handleClearCommand(event: SlashCommandInteractionEvent) {
        val channel = event.channel.asTextChannel()
        val amount = event.getOption("amount")?.asInt
            ?: run {
                event.reply("Please specify a valid number of messages to clear.").setEphemeral(true).queue()
                return
            }

        try {
            channel.purgeMessages(channel.history.retrievePast(amount).complete())
            event.reply("Cleared $amount messages successfully.").queue()
        } catch (_: Exception) {
            event.reply("Failed to clear messages.").setEphemeral(true).queue()
        }
    }
}
// register the command in your main application or bot setup