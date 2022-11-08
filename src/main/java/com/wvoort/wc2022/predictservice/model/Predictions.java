package com.wvoort.wc2022.predictservice.model;

import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Predictions {

    private List<Prediction> predictions = new ArrayList<>();

    public Predictions() {

    }

    public static String toJson(Predictions predictions) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Instant.class, new InstantSerializer());
        return gsonBuilder.create().toJson(predictions);
    }



}
