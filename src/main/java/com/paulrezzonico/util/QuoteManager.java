package com.paulrezzonico.util;

import com.paulrezzonico.DataProvider.IDataProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class QuoteManager {

    @Autowired
    private IDataProvider dataProvider;

    public String getRandomQuote() {

        List<String> quotes = dataProvider.getData("/voiceLines.txt");

        if (!quotes.isEmpty()) {
            Random random = new Random();
            return quotes.get(random.nextInt(quotes.size()));
        }

        return "No quotes found";
    }
}
