package com.paulrezzonico.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class QuoteManager {

    private static final Logger logger = LoggerFactory.getLogger(QuoteManager.class);

    public String getRandomQuote() {
        List<String> quotes = new ArrayList<>();
        try {
            InputStream in = getClass().getResourceAsStream("/VoiceLines.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(in)));

            String line;
            while ((line = reader.readLine()) != null) {
                quotes.add(line);
            }
            reader.close();
        } catch (Exception e) {
            logger.error("Error while reading quotes file", e);
        }

        if (!quotes.isEmpty()) {
            Random random = new Random();
            return quotes.get(random.nextInt(quotes.size()));
        }

        return "No quotes found";
    }
}
