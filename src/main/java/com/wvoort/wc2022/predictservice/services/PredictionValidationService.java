package com.wvoort.wc2022.predictservice.services;

import com.wvoort.wc2022.predictservice.model.Prediction;
import com.wvoort.wc2022.predictservice.model.PredictionException;
import com.wvoort.wc2022.predictservice.model.PredictionPolicyFactory;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PredictionValidationService {

    @Autowired
    private PredictionPolicyFactory predictionPolicyFactory;

    public List<ErrorDetails> validatePrediction(Prediction prediction, int index) {
        List<ErrorDetails> errorDetailsList = new ArrayList<>();
        if(prediction.getMatchId() == null) {
            errorDetailsList.add(new ErrorDetails("predictions[" + index + "]", "matchId is invalid"));
        }

        if (prediction.getHomeGoals() != null && (prediction.getHomeGoals() < 0 || prediction.getHomeGoals() > 10)) {
            errorDetailsList.add(new ErrorDetails("predictions[" + index + "].homeGoals", "scores should be between 0 and 10"));
        }

        if (prediction.getAwayGoals() != null && (prediction.getAwayGoals() < 0 || prediction.getAwayGoals() > 10)) {
            errorDetailsList.add(new ErrorDetails("predictions[" + index + "].awayGoals", "scores should be between 0 and 10"));
        }

        return errorDetailsList;
    }


    public List<ErrorDetails> validatePredictions(List<Prediction> predictions) throws PredictionException {
        predictionPolicyFactory.createPredictionPolicy().apply();
        List<ErrorDetails> errorDetailsList = new ArrayList<>();
        int index = 0;
        for(Prediction prediction: predictions) {
            errorDetailsList.addAll(validatePrediction(prediction, index));
            index++;
        }

        return errorDetailsList;
    }


    @AllArgsConstructor
    @Data
    public static class ErrorDetails {
        private String fieldName;
        private String errorDescription;
    }
}
