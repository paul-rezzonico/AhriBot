package com.paulrezzonico.command.admin

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import net.dv8tion.jda.internal.entities.UserSnowflakeImpl
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class BanCommand : ListenerAdapter() {
    override fun onSlashCommandInteraction(event: SlashCommandInteractionEvent) {
        if (AdminPermissionUtils.isNotAdmin(event)) return
        if (event.name != "ban") return
        handleBanCommand(event)
    }

    private fun handleBanCommand(event: SlashCommandInteractionEvent) {
        val member = event.getOption("user")?.asMember
        if (member == null) {
            event.reply("User not found.").setEphemeral(true).queue()
            return
        }
        if (member.user.id == event.jda.selfUser.id) {
            event.reply("You cannot ban the bot itself.").setEphemeral(true).queue()
            return
        }
        if (member.user is UserSnowflakeImpl && member.user.id == event.member!!.id) {
            event.reply("You cannot ban yourself.").setEphemeral(true).queue()
            return
        }
        if (member.user.isBot) {
            event.reply("You cannot ban a bot.").setEphemeral(true).queue()
            return
        }
        if (member.roles.any { it.isManaged }) {
            event.reply("You cannot ban a member with managed roles.").setEphemeral(true).queue()
            return
        }

        val reason = event.getOption("reason")?.asString ?: "No reason provided"
        if (reason.length > 512) {
            event.reply("Reason is too long, must be 512 characters or less.").setEphemeral(true).queue()
            return
        }

        val deleationDays = event.getOption("deletion-days")?.asInt ?: 0

        val guild = event.guild ?: return
        guild.ban(member.user, deleationDays, TimeUnit.DAYS)
            .reason(reason).queue(
            { event.reply("Banned ${member.asMention} successfully.").queue() },
            { event.reply("Failed to ban ${member.asMention}.").setEphemeral(true).queue() }
        )
    }
}

