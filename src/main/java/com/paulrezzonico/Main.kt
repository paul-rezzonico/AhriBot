package com.paulrezzonico

import com.paulrezzonico.command.admin.BanCommand
import com.paulrezzonico.command.admin.KickCommand
import io.mongock.runner.springboot.EnableMongock
import com.paulrezzonico.command.admin.MuteCommand
import com.paulrezzonico.command.admin.UnmuteCommand
import com.paulrezzonico.command.ahri.quote.RandomQuoteCommand
import com.paulrezzonico.command.casual.pat.PatCommand
import com.paulrezzonico.command.information.PingCommand
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.requests.GatewayIntent
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling

@EnableMongock
@SpringBootApplication
@EnableScheduling
@Suppress("SpreadOperator", "MemberNameEqualsClassName")
open class Main(
    private val randomQuoteCommand: RandomQuoteCommand,
    private val patCommand: PatCommand,
    private val muteCommand: MuteCommand,
    private val unmuteCommand: UnmuteCommand,
    private val kickCommand: KickCommand,
    private val banCommand: BanCommand,
    private val pingCommand: PingCommand,
) {
    @Value("\${discord.bot.token}")
    private val token: String? = null

    @Bean
    @Throws(Exception::class)
    open fun jda(): JDA {
        val jda = JDABuilder.createDefault(token)
            .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
            .addEventListeners(
                randomQuoteCommand,
                patCommand,
                muteCommand,
                unmuteCommand,
                kickCommand,
                banCommand,
                pingCommand,
            )
            .build()
            .awaitReady()

        registerSlashCommands(jda)
        return jda
    }

    private fun registerSlashCommands(jda: JDA) {
        jda.updateCommands().addCommands(
            Commands.slash("quote", "Get a random quote from Ahri"),
            Commands.slash("pat", "Pat someone")
                .addOption(OptionType.USER, "user", "The user to pat", true),
            Commands.slash("mute", "Mute a user")
                .addOption(OptionType.USER, "user", "The user to mute", true)
                .addOption(OptionType.INTEGER, "duration", "The duration of the mute in minutes", true),
            Commands.slash("unmute", "Unmute a user")
                .addOption(OptionType.USER, "user", "The user to unmute", true),
            Commands.slash("kick", "Kick a user")
                .addOption(OptionType.USER, "user", "The user to kick", true),
            Commands.slash("ban", "Ban a user")
                .addOption(OptionType.USER, "user", "The user to ban", true)
                .addOption(OptionType.STRING, "reason", "The reason for the ban", false)
                .addOption(OptionType.INTEGER, "deletion-days", "Number of days of messages to delete (0-7)", false),
            Commands.slash("ping", "Check the bot's latency"),
        ).queue()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Main::class.java, *args)
        }
    }
}
