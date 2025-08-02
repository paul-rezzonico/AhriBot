package com.paulrezzonico.command.admin

import com.paulrezzonico.command.admin.Constants.MAX_REASON_LENGTH
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
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
        val guild = event.guild
        val selfId = event.jda.selfUser.id
        val authorId = event.member?.id

        when {
            member == null -> replyError(event, "User not found.")
            member.user.id == selfId -> replyError(event, "You cannot ban the bot itself.")
            member.user.id == authorId -> replyError(event, "You cannot ban yourself.")
            member.user.isBot -> replyError(event, "You cannot ban a bot.")
            member.roles.any { it.isManaged } -> replyError(event, "You cannot ban a member with managed roles.")
            else -> {
                val reason = event.getOption("reason")?.asString ?: "No reason provided"
                if (reason.length > MAX_REASON_LENGTH) {
                    replyError(event, "Reason is too long, must be $MAX_REASON_LENGTH characters or less.")
                    return
                }
                val deletionDays = event.getOption("deletion-days")?.asInt ?: 0
                guild?.ban(member.user, deletionDays, TimeUnit.DAYS)
                    ?.reason(reason)
                    ?.queue(
                        { event.reply("Banned ${member.asMention} successfully.").queue() },
                        { event.reply("Failed to ban ${member.asMention}.").setEphemeral(true).queue() }
                    )
            }
        }
    }

    private fun replyError(event: SlashCommandInteractionEvent, message: String) {
        event.reply(message).setEphemeral(true).queue()
    }
}

