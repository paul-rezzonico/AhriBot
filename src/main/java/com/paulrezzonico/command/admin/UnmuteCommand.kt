package com.paulrezzonico.command.admin

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component

@Component
class UnmuteCommand : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (AdminPermissionUtils.isNotAdmin(event)) return
        if (event.name != "unmute") return
        handleUnMuteCommand(event)
    }

    private fun handleUnMuteCommand(event: SlashCommandInteractionEvent) {
        val member = event.getOption("user")?.asMember
        if (member == null) {
            event.reply("User not found.").setEphemeral(true).queue()
            return
        }

        val guild = event.guild ?: return
        for (channel in guild.textChannels) {
            channel.upsertPermissionOverride(member)
                .clear(Permission.MESSAGE_SEND)
                .queue()
        }
        event.reply("Unmuted ${member.asMention}.").queue()
    }
}
