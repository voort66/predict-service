package com.wvoort.wc2022.predictservice.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

@Data
@Entity
public class Match implements Serializable {

    @Id
    private Long matchId;

    private String awayTeamName;

    private String venueName;

    private String homeTeamLogo;

    private String awayTeamLogo;

    private String homeTeamName;

    private String startDate;

    private String startTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return getMatchId().equals(match.getMatchId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMatchId());
    }
}
