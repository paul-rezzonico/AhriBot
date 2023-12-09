package com.paulrezzonico.util

import com.paulrezzonico.dataProvider.IDataProvider
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.entities.Activity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*

@Component
class StatusManager {
    @Autowired
    private val dataProvider: IDataProvider? = null

    @Autowired
    private val jda: JDA? = null

    @Scheduled(fixedRate = 3600000) // Change status every hour
    fun updateStatus() {
        val newStatus = status
        jda!!.presence.activity = Activity.playing(newStatus)
    }

    private val status: String
        get() {
            val status = dataProvider!!.getData("/status.txt")

            if (status.isNotEmpty()) {
                val random = Random()
                return status[random.nextInt(status.size)]
            }

            return "Guiding you through spirit world..."
        }
}
