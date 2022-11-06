package com.wvoort.wc2022.predictservice.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

class BeforeStartPolicyTest {

    @Test
    void testAfterStart() {
        BeforeStartPolicy beforeStartPolicy = new BeforeStartPolicy();
        PredictionException thrown = Assertions.assertThrows(PredictionException.class, () -> {
            beforeStartPolicy.setTournamentStartTime(LocalDateTime.now().minus(23, ChronoUnit.HOURS));
            beforeStartPolicy.apply();
        });

        Assertions.assertEquals("Bets are closed. Tournament has started.", thrown.getMessage());
    }

    @Test
    void testBeforeStart() throws PredictionException {
        BeforeStartPolicy beforeStartPolicy = new BeforeStartPolicy();
        beforeStartPolicy.apply();

    }

    @Test
    void testAtStart()  {
        BeforeStartPolicy beforeStartPolicy = new BeforeStartPolicy();

        PredictionException thrown = Assertions.assertThrows(PredictionException.class, () -> {
            beforeStartPolicy.setTournamentStartTime(LocalDateTime.now().minus(1, ChronoUnit.SECONDS));
            beforeStartPolicy.apply();
        });

        Assertions.assertEquals("Bets are closed. Tournament has started.", thrown.getMessage());
    }

}