package com.wvoort.wc2022.predictservice.model;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class Matches implements Serializable {

    private List<Match> matchesList = new ArrayList<>();

    private Map<Long, Match> matchMap = new HashMap<>();


    private void init() {
        matchMap = matchesList.stream().collect(Collectors.toMap(Match::getMatchId, Function.identity()));
    }

    public Match getMatchById(Long id) {
        log.info("Match id = {}", id);
        Match match =  matchMap.get(id);
        log.info(match == null? " Match not found": "Match found");
        return match;

    }

    public Matches getMatchesForRound(Round round) {
        Matches matches = new Matches();
        matches.matchesList = this.matchesList.stream().filter(m -> m.startDateTimeAsInstant().isAfter(round.getStart()) && m.startDateTimeAsInstant().isBefore(round.getEnd())).collect(Collectors.toList());
        matches.init();
        return matches;
    }

    public List<Match> getMatchesWithoutPredictions(List<Prediction> predictions) {
        Set<Long> predictedMatchIds = predictions.stream().map(p -> p.getMatchId()).collect(Collectors.toSet());
        return matchesList.stream().filter(m -> !predictedMatchIds.contains(m.getMatchId())).collect(Collectors.toList());

    }

    public static Matches fromJsonResponseString(String matchesJson) {
        Matches matches = new Gson().fromJson(matchesJson, Matches.class);
        matches.init();
        return matches;

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
