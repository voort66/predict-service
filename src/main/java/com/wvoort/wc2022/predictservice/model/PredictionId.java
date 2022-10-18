package com.wvoort.wc2022.predictservice.model;

import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.io.Serializable;

@EqualsAndHashCode
public class PredictionId implements Serializable {

    private Long matchId;

    private String userName;

    public PredictionId() {

    }

    public PredictionId(Long matchId, String userName) {
        this.matchId = matchId;
        this.userName = userName;
    }
}
