package com.wvoort.wc2022.predictservice.model;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Transient;
import java.time.Instant;

@Entity
@IdClass(PredictionId.class)
@EqualsAndHashCode
@Data
public class Prediction implements Comparable<Prediction> {

    public Prediction() {
        //noop
    }

    public Prediction(Long matchId, String userName, Match matchDetails) {
        this.matchId = matchId;
        this.userName = userName;
        this.matchDetails = matchDetails;
    }


    @Id
    private Long matchId;

    @Id
    private String userName;


    @Min(value = 0)
    @Max(value = 10)
    private Integer homeGoals;


    @Min(value = 0)
    @Max(value = 10)
    private Integer awayGoals;

    private Instant creationDate;

    private Instant lastUpdate;

    @Transient
    @Setter
    @Getter
    private Match matchDetails;


    @Override
    public int compareTo(Prediction p) {
        if(matchId > p.matchId) {
            return 1;
        } else if (matchId < p.matchId) {
            return -1;
        }

        return 0;
    }
}
