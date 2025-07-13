package com.paulrezzonico.service.status

import com.paulrezzonico.repository.StatusRepository
import com.paulrezzonico.service.DiscordBotService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class StatusService(
    private val statusRepository: StatusRepository,
    private val discordBotService: DiscordBotService
) {
    @Scheduled(fixedRate = 3600000)
    fun updateStatus() {
        val newStatus = getRandomStatus()
        discordBotService.updateBotStatus(newStatus)
    }


    fun getRandomStatus(): String {
        val allStatus = statusRepository.findAll()
        return if (allStatus.isNotEmpty()) {
            allStatus.random().status
        } else {
            "Guiding you through spirit world..."
        }
    }

    private val status: String
        get() {
            val status = getRandomStatus()
            return status.ifEmpty {
                "Guiding you through spirit world..."
            }
        }
}
