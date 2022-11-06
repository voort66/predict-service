package com.wvoort.wc2022.predictservice.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class RoundsTest {

    private Rounds rounds;

    @BeforeEach
    void setUp() throws IOException {
        String roundsJsonString = Files.readString(Paths.get("build/resources/test/rounds.json"));
        rounds = Rounds.fromJsonResponseString(roundsJsonString);
    }

    @Test
    void testRoundsFromJson() throws IOException {

        assertNotNull(rounds);
        assertEquals(6, rounds.getRounds().size());

    }

    @Test
    void testCurrentRound() {
        Round round = rounds.getCurrentRound();
        assertNull(round);
    }

    @Test
    void testNextRound() {
        Round round = rounds.getNextPredictionRound();
        assertNotNull(round);
        assertEquals("group", round.getStage());

    }

    @Test
    void testIsTournamentFinished() {
        assertFalse(rounds.isTournamentFinished());

    }

    @Test
    void testFirstRound() {
        Round round = rounds.getFirstRound();
        assertNotNull(round);
        assertEquals("group", round.getStage());

    }

    @Test
    void testLastRound() {
        Round round = rounds.getLastRound();
        assertNotNull(round);
        assertEquals("final", round.getStage());

    }
}