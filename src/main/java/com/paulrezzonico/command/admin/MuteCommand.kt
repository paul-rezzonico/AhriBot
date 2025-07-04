package com.paulrezzonico.command.admin

import net.dv8tion.jda.api.Permission
import net.dv8tion.jda.api.entities.Member
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class MuteCommand : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (isNotAllowed(event)) return
        if (event.name != "mute") return
        handleMuteCommand(event)
    }

    private fun handleMuteCommand(event: SlashCommandInteractionEvent) {
        val member = event.getOption("user")!!.asMember
        val duration = parseDuration(event.getOption("duration")!!.asString)

        if (member == null || duration <= 0) {
            event.reply("User not found or invalid duration.").queue()
            return
        }

        val guild = event.guild ?: return
        for (channel in guild.channels) {
            if (channel is TextChannel) {
                handleMute(channel, member, duration, event)
            }
        }
        event.reply("Muted ${member.asMention} for $duration minutes.").queue()
    }

    private fun isNotAllowed(event: SlashCommandInteractionEvent): Boolean {
        if (!event.member!!.hasPermission(Permission.MANAGE_PERMISSIONS)) {
            event.reply("You don't have the permission to use this command.").queue()
            return true
        }
        return false
    }

    private fun handleMute(channel: TextChannel, member: Member, duration: Long, event: SlashCommandInteractionEvent) {
        channel.upsertPermissionOverride(member)
            .deny(Permission.MESSAGE_SEND)
            .queue()

        event.jda.rateLimitPool.schedule({
            channel.upsertPermissionOverride(member)
                .clear(Permission.MESSAGE_SEND)
                .queue()
        }, duration, TimeUnit.MINUTES)
    }

    private fun parseDuration(durationStr: String): Long {
        return try {
            durationStr.toLong()
        } catch (_: NumberFormatException) {
            -1
        }
    }
}
