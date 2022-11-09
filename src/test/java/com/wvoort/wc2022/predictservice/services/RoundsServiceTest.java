package com.wvoort.wc2022.predictservice.services;

import com.wvoort.wc2022.predictservice.model.Rounds;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundsServiceTest {




    @Test
    void testReadRoundsFile() {
        RoundsService roundsService = new RoundsService();
        Rounds rounds = roundsService.getRounds();
        assertNotNull(rounds);
        assertEquals(6, rounds.getRounds().size());
    }

}