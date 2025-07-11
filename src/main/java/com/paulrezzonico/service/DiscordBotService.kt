package com.paulrezzonico.service

import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Activity
import org.springframework.stereotype.Service

@Service
class DiscordBotService(private val jda: JDA) {
    fun updateBotStatus(status: String) {
        jda.presence.activity = Activity.playing(status)
    }
}