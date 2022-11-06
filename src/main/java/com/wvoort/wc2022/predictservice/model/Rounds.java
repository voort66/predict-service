package com.wvoort.wc2022.predictservice.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Rounds implements Serializable {
    private List<Round> rounds = new ArrayList<>();

    public static Rounds fromJsonResponseString(String roundsJson) {
        if(roundsJson == null) {
            return new Rounds();
        }

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantDeserializer());

        var rounds = gsonBuilder.create().fromJson(roundsJson, Rounds.class);
        return rounds;

    }

    public Round getCurrentRound() {
        Optional<Round> currentRound = rounds.stream().filter(Round::isCurrent).findFirst();

        return currentRound.orElse(null);
    }

    public List<Round> getRounds() {
        return Collections.unmodifiableList(rounds.stream().sorted().collect(Collectors.toList()));
    }

    public Round getNextPredictionRound() {
        final Instant now = Instant.now();
        Optional<Round> nextRound = getRounds().stream().filter(Round::canPredict).findFirst();
        return nextRound.orElse(null);
    }

    public boolean isTournamentFinished() {
        Instant now = Instant.now();
        Round lastRound = getLastRound();
        return now.isAfter(lastRound.getEnd());

    }

    public Round getLastRound() {
         Optional<Round> lastRound = getRounds().stream().reduce((first, second) -> second);
         return lastRound.orElseThrow();
    }

    public Round getFirstRound() {
        return getRounds().iterator().next();
    }

}
