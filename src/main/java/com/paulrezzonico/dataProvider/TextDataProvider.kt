package com.paulrezzonico.DataProvider;

import com.paulrezzonico.util.QuoteManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class TextDataProvider implements IDataProvider {

    private static final Logger logger = LoggerFactory.getLogger(QuoteManager.class);
    @Override
    public List<String> getData(String path) {
        List<String> data = new ArrayList<>();
        try {
            InputStream in = getClass().getResourceAsStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(in)));

            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line);
            }
            reader.close();
        } catch (Exception e) {
            logger.error("Error while reading quotes file", e);
        }

        return data;
    }
}
