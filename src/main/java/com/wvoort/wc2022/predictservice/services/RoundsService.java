package com.wvoort.wc2022.predictservice.services;

import com.wvoort.wc2022.predictservice.model.Rounds;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@Service
public class RoundsService {


    public Rounds getRounds() {

        return Rounds.fromJsonResponseString(getRawRounds());
    }

    private String getRawRounds() {
        try {
            return Files.readString(Paths.get("build/resources/main/rounds.json"));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }
}
