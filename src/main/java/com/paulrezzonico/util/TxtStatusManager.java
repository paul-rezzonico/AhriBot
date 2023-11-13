package com.paulrezzonico.util;

import com.paulrezzonico.facade.IStatusFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Component
public class TxtStatusManager implements IStatusFacade {

    private static final Logger logger = LoggerFactory.getLogger(com.paulrezzonico.util.TxtQuoteManager.class);

    public String getStatus() {
        List<String> status = new ArrayList<>();
        try {
            InputStream in = getClass().getResourceAsStream("/status.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(in)));

            String line;
            while ((line = reader.readLine()) != null) {
                status.add(line);
            }
            reader.close();
        } catch (Exception e) {
            logger.error("Error while reading status file", e);
        }

        if (!status.isEmpty()) {
            Random random = new Random();
            return status.get(random.nextInt(status.size()));
        }

        return "Guiding you through spirit world...";
    }
}
