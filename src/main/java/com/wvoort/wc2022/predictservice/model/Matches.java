package com.wvoort.wc2022.predictservice.model;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Matches {

    private List<Match> matchesList = new ArrayList<>();

    public Match getMatchById(Long id) {
        return matchesList.stream().filter(m -> m.getMatchId().equals(id)).findFirst().get();
    }

    public List<Match> getMatchesWithoutPredictions(List<Prediction> predictions) {
        Set<Long> predictedMatchIds = predictions.stream().map(p -> p.getMatchId()).collect(Collectors.toSet());
        return matchesList.stream().filter(m -> !predictedMatchIds.contains(m.getMatchId())).collect(Collectors.toList());
    }

    public static Matches fromJsonResponseString(String matchesJson) {
        return new Gson().fromJson(matchesJson, Matches.class);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matches matches = (Matches) o;
        return Objects.equals(matchesList, matches.matchesList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(matchesList);
    }
}
