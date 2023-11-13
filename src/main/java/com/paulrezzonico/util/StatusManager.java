package com.paulrezzonico.util;

import com.paulrezzonico.DataProvider.IDataProvider;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class StatusManager {
@Autowired
    private IDataProvider dataProvider;

    @Autowired
    private JDA jda;

    @Scheduled(fixedRate = 300000) // Change status every 5 minutes
    public void updateStatus() {
        String newStatus = getStatus();
        jda.getPresence().setActivity(Activity.playing(newStatus));
    }

    public String getStatus() {
        List<String> status = dataProvider.getData("/status.txt");

        if (!status.isEmpty()) {
            Random random = new Random();
            return status.get(random.nextInt(status.size()));
        }

        return "Guiding you through spirit world...";
    }
}
