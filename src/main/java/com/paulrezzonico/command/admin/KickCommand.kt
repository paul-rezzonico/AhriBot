package com.paulrezzonico.command.admin

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component

@Component
class KickCommand : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (AdminPermissionUtils.isNotAdmin(event)) return
        if (event.name != "kick") return
        handleKickCommand(event)
    }

    private fun handleKickCommand(event: SlashCommandInteractionEvent) {
        val member = event.getOption("user")?.asMember
        if (member == null) {
            event.reply("User not found.").setEphemeral(true).queue()
            return
        }

        val guild = event.guild ?: return
        guild.kick(member).queue(
            { event.reply("Kicked ${member.asMention} successfully.").queue() },
            { event.reply("Failed to kick ${member.asMention}.").setEphemeral(true).queue() }
        )
    }
}
