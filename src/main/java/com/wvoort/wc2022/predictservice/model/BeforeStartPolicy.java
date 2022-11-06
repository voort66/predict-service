package com.wvoort.wc2022.predictservice.model;

import lombok.AccessLevel;
import lombok.Setter;

import java.time.LocalDateTime;

public class BeforeStartPolicy implements Policy {

    @Setter(AccessLevel.PACKAGE)
    private LocalDateTime tournamentStartTime = LocalDateTime.of(2022, 11, 20, 16, 0, 0);

    @Override
    public void apply() throws PredictionException {
        LocalDateTime now = LocalDateTime.now();
        if(now.isAfter(tournamentStartTime)) {
            throw new PredictionException("Bets are closed. Tournament has started.");
        }
    }
}
