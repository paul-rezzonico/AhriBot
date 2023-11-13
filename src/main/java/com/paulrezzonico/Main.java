package com.paulrezzonico;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
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
    public JDA jda() {
        return JDABuilder.createDefault(token).build();
    }
}