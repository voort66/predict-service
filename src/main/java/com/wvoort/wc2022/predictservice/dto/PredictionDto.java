package com.wvoort.wc2022.predictservice.dto;

import com.wvoort.wc2022.predictservice.model.Prediction;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class PredictionDto {

    private String user;

    private List<Prediction> predictions;

    public PredictionDto() {
        predictions = new ArrayList();
    }



    public void addPrediction(Prediction prediction) {
        predictions.add(prediction);
    }




}
