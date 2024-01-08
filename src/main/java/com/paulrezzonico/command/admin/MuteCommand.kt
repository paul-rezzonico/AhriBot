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
        if (!event.member!!.hasPermission(Permission.MANAGE_PERMISSIONS)) {
            event.reply("You don't have the permission to use this command.").queue()
            return
        }

        if (event.name == "mute") {
            val member = event.getOption("user")!!.asMember
            val duration = parseDuration(event.getOption("duration")!!.asString)

            if (member != null && duration > 0) {
                val guild = event.guild
                if (guild != null) {
                    for (channel in guild.channels) {
                        if (channel is TextChannel) {
                            handleMute(channel, member, duration, event)
                        }
                    }

                    event.reply("Muted " + member.asMention + " for " + duration + " minutes.").queue()
                }
            } else {
                event.reply("User not found or invalid duration.").queue()
            }
        }
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
        } catch (e: NumberFormatException) {
            -1
        }
    }
}
