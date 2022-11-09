package com.wvoort.wc2022.predictservice.services;

import com.wvoort.wc2022.predictservice.model.*;
import com.wvoort.wc2022.predictservice.repository.PredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PredictionService {


    @Autowired
    private PredictionRepository predictionRepository;

    @Autowired
    private MatchService matchService;

    @Autowired
    private RoundsService roundsService;


    public Predictions getEditablePredictions(String userName) {

        Rounds rounds = getRounds();
        if(rounds.isTournamentFinished()) {
            return new Predictions();
        }

        Round round =  rounds.getNextPredictionRound();
        final Matches matches = getMatchesInRound(round);

        List<Prediction> predictions = predictionRepository.findByUserName(userName);
        List<Prediction> predictionsInRoundScope =
                predictions.stream().filter(p -> p.isPredictionInMatchesScope(matches)).collect(Collectors.toList());

        predictionsInRoundScope.forEach(p -> p.updateMatchDetails(matches));

        predictionsInRoundScope.addAll(
                matches.getMatchesWithoutPredictions(predictionsInRoundScope).stream()
                        .map(m -> new Prediction(m.getMatchId(), userName, m)).collect(Collectors.toList()));

        return new Predictions(predictionsInRoundScope.stream().sorted().collect(Collectors.toList()));
    }

    public Predictions getAllPredictions(String userName) {

        final Matches matches = getMatches();

        List<Prediction> predictions = predictionRepository.findByUserName(userName);

        predictions.forEach(p -> p.updateMatchDetails(matches));

        predictions.addAll(
                matches.getMatchesWithoutPredictions(predictions).stream()
                        .map(m -> new Prediction(m.getMatchId(), userName, m)).collect(Collectors.toList()));

        return new Predictions(predictions.stream().sorted().collect(Collectors.toList()));
    }

    public Predictions getPredictionsForAllUsers() {
        List<Prediction> predictions = predictionRepository.findAll();
        final Matches matches = getMatches();
        predictions.forEach(p -> p.updateMatchDetails(matches));

        return new Predictions(predictions.stream().sorted().collect(Collectors.toList()));

    }



    public Predictions createPredictions(Predictions predictions) {


        List<Prediction> completePredictions = predictions.getPredictions().stream().filter(Prediction::isComplete).filter(this::isChanged).
                map(this::updateTimestamps).collect(Collectors.toList());
        Matches matches = getMatches();
        List<Prediction> savedPredictions =
                predictionRepository.saveAll(completePredictions);
        savedPredictions.forEach(p -> p.updateMatchDetails(matches));
        return new Predictions(savedPredictions);


    }

    public Prediction updatePredictionWithMatchDetails(Prediction p) {
        if (p.getMatchDetails() != null) {
            return p;
        }


        p.setMatchDetails(getMatches().getMatchById(p.getMatchId()));
        return p;


    }


    private boolean isChanged(Prediction prediction) {
        Prediction savedPrediction = predictionRepository.findById(new PredictionId(prediction.getMatchId(), prediction.getUserName())).orElse(null);
        if(savedPrediction == null) {
            return true;
        }

        return !savedPrediction.getAwayGoals().equals(prediction.getAwayGoals()) ||
                !savedPrediction.getHomeGoals().equals(prediction.getHomeGoals());

    }

    private Prediction updateTimestamps(Prediction prediction) {
        Prediction savedPrediction = predictionRepository.findById(new PredictionId(prediction.getMatchId(), prediction.getUserName())).orElse(null);
        Instant now = Instant.now();
        if (savedPrediction != null) {

            if (savedPrediction.getCreationDate() != null) {
                prediction.setCreationDate(savedPrediction.getCreationDate());
            }

            if (!savedPrediction.getAwayGoals().equals(prediction.getAwayGoals()) ||
                    !savedPrediction.getHomeGoals().equals(prediction.getHomeGoals())) {
                prediction.setLastUpdate(now);

            }
        } else {
            prediction.setCreationDate(now);
            prediction.setLastUpdate(now);
        }

        return prediction;
    }

    private Matches getMatches() {
        return matchService.getMatches();
    }

    private Rounds getRounds() {
        return roundsService.getRounds();
    }

    private Matches getMatchesInRound(final Round round) {
        Matches matches = getMatches();
        return matches.getMatchesForRound(round);

    }

}
