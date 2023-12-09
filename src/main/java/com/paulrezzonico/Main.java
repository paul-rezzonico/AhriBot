package com.paulrezzonico;

import com.paulrezzonico.command.casual.PatCommand;
import com.paulrezzonico.command.ahri.RandomQuoteCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Main {

    @Value("${discord.bot.token}")
    private String token;

    @Autowired
    private RandomQuoteCommand randomQuoteCommand;

    @Autowired
    private PatCommand patCommand;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public JDA jda() throws Exception {
        JDA jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(
                        randomQuoteCommand,
                        patCommand
                )
                .build()
                .awaitReady();

        registerSlashCommands(jda);
        return jda;
    }

    private void registerSlashCommands(JDA jda) {
        jda.updateCommands().addCommands(
                Commands.slash("quote", "Get a random quote from Ahri"),
                Commands.slash("pat", "Pat someone")
                        .addOption(OptionType.USER, "user", "The user to pat", true),
                Commands.slash("mute", "Mute a user")
                        .addOption(OptionType.USER, "user", "The user to mute", true)
                        .addOption(OptionType.INTEGER, "duration", "The duration of the mute in minutes", true)
        ).queue();
    }
}
