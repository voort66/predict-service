package com.wvoort.wc2022.predictservice.services;

import com.wvoort.wc2022.predictservice.model.Matches;
import com.wvoort.wc2022.predictservice.model.Prediction;
import com.wvoort.wc2022.predictservice.model.PredictionId;
import com.wvoort.wc2022.predictservice.repository.PredictionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PredictionService {


    @Autowired
    private PredictionRepository predictionRepository;

    @Autowired
    private MatchService matchService;


    public List<Prediction> getPredictions(String userName) {
        Matches matches = matchService.getMatches();

        List<Prediction> predictions = predictionRepository.findByUserName(userName);
        predictions.forEach(p -> {
            p.setMatchDetails(matches.getMatchById(p.getMatchId()));
        });

        predictions.addAll(
                matches.getMatchesWithoutPredictions(predictions).stream()
                        .map(m -> new Prediction(m.getMatchId(), userName, m)).collect(Collectors.toList()));



        return predictions.stream().sorted().collect(Collectors.toList());
    }

    public void createPredictions(List<Prediction> predictions) {

        predictions.stream().filter(p -> p.getAwayGoals() != null && p.getHomeGoals() != null).map(this::updateTimestamps).
                forEach(predictionRepository::save);


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


}
