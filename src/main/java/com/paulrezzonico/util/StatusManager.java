package com.paulrezzonico.util;

import com.paulrezzonico.DataProvider.IDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class StatusManager {

    @Autowired
    private IDataProvider dataProvider;

    public String getStatus() {

        List<String> status = dataProvider.getData("/status.txt");

        if (!status.isEmpty()) {
            Random random = new Random();
            return status.get(random.nextInt(status.size()));
        }

        return "Guiding you through spirit world...";
    }
}
