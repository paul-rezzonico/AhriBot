package com.paulrezzonico;

import com.paulrezzonico.command.RandomQuoteCommand;
import com.paulrezzonico.util.TxtStatusManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    @Value("${discord.bot.token}")
    private String token;

    @Autowired
    private RandomQuoteCommand randomQuoteCommand;

    @Autowired
    private TxtStatusManager statusManager;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public JDA jda() throws Exception {
        JDA jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.GUILD_MESSAGES, GatewayIntent.DIRECT_MESSAGES)
                .setActivity(Activity.playing(statusManager.getStatus()))
                .addEventListeners(randomQuoteCommand)
                .build()
                .awaitReady();

        registerSlashCommands(jda);
        return jda;
    }

    private void registerSlashCommands(JDA jda) {
        jda.updateCommands().addCommands(
                Commands.slash("quote", "Get a random quote from the Ahri")
        ).queue();
    }
}
