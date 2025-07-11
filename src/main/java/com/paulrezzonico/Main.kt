package com.paulrezzonico

import com.paulrezzonico.command.admin.BanCommand
import com.paulrezzonico.command.admin.KickCommand
import io.mongock.runner.springboot.EnableMongock
import com.paulrezzonico.command.admin.MuteCommand
import com.paulrezzonico.command.ahri.quote.RandomQuoteCommand
import com.paulrezzonico.command.casual.pat.PatCommand
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.interactions.commands.OptionType
import net.dv8tion.jda.api.interactions.commands.build.Commands
import net.dv8tion.jda.api.requests.GatewayIntent
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.scheduling.annotation.EnableScheduling

@EnableMongock
@SpringBootApplication
@EnableScheduling
@Suppress("SpreadOperator", "MemberNameEqualsClassName")
open class Main {
    @Value("\${discord.bot.token}")
    private val token: String? = null

    @Autowired
    private val randomQuoteCommand: RandomQuoteCommand? = null

    @Autowired
    private val patCommand: PatCommand? = null

    @Autowired
    private val muteCommand: MuteCommand? = null

    @Autowired
    private val kickCommand: KickCommand? = null

    @Autowired
    private val banCommand: BanCommand? = null

    @Bean
    @Throws(Exception::class)
    open fun jda(): JDA {
        val jda = JDABuilder.createDefault(token)
            .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
            .addEventListeners(
                randomQuoteCommand,
                patCommand,
                muteCommand,
                kickCommand,
                banCommand,
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
            Commands.slash("kick", "Kick a user")
                .addOption(OptionType.USER, "user", "The user to kick", true),
            Commands.slash("ban", "Ban a user")
                .addOption(OptionType.USER, "user", "The user to ban", true)
                .addOption(OptionType.STRING, "reason", "The reason for the ban", false)
                .addOption(OptionType.INTEGER, "deletion-days", "Number of days of messages to delete (0-7)", false)
        ).queue()
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(Main::class.java, *args)
        }
    }
}
