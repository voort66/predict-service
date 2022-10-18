package com.wvoort.wc2022.predictservice.model;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class MatchesTest {

    @Test
    public void testMatchesFromJson() throws IOException {
        String matchesJsonString = Files.readString(Paths.get("build/resources/test/matches.json"));

        Matches matches = Matches.fromJsonResponseString(matchesJsonString);
        assertNotNull(matches);
        assertEquals(48, matches.getMatchesWithoutPredictions(Collections.emptyList()).size());



    }
}