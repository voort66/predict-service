package com.wvoort.wc2022.predictservice.services;

import com.wvoort.wc2022.predictservice.model.Rounds;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service
public class RoundsService {


    public Rounds getRounds() {

        return Rounds.fromJsonResponseString(getRawRounds());
    }

    private String getRawRounds() {
        StringBuilder rawRounds = new StringBuilder();
        try {

            InputStream inputStream = this.getClass().getResourceAsStream("/rounds.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while((line = reader.readLine()) != null) {

                rawRounds.append(line);
                rawRounds.append(System.lineSeparator());
            }


        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return rawRounds.toString();
    }
}
