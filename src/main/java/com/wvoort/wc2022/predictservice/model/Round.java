package com.wvoort.wc2022.predictservice.model;

import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

@Data
public class Round implements Serializable, Comparable<Round> {
    private String stage;
    private Instant start;
    private Instant end;
    private Integer games;

    @Override
    public int compareTo(Round round) {
        return start.compareTo(round.start);
    }

    public boolean isCurrent() {
        Instant now = Instant.now();
        return now.isAfter(start) && now.isBefore(end);
    }

    public boolean canPredict() {
        Instant now = Instant.now();
        return now.isBefore(start);
    }

}
