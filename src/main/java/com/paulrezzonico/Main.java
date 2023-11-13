package com.paulrezzonico;

import com.paulrezzonico.command.RandomQuoteCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    @Value("${discord.bot.token}")
    private String token;
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
    @Bean
    public JDA jda() throws InterruptedException {
        return JDABuilder.createDefault(token)
                .addEventListeners(new RandomQuoteCommand())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build()
                .awaitReady();
    }
}