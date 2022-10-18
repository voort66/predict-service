package com.wvoort.wc2022.predictservice.repository;

import com.wvoort.wc2022.predictservice.model.Match;
import com.wvoort.wc2022.predictservice.model.Prediction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class PredictionRepositoryTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PredictionRepository predictionRepository;

    private Prediction prediction;

    @BeforeEach
    void setUp() {
        prediction = new Prediction();
        prediction.setMatchId(20L);
        prediction.setHomeGoals(3);
        prediction.setAwayGoals(2);

        Match match = new Match();
        match.setMatchId(20L);

        prediction.setMatchDetails(match);

    }

    @Test
    void injectedComponentsAreNotNull(){
        assertThat(dataSource).isNotNull();
        assertThat(jdbcTemplate).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(predictionRepository).isNotNull();
    }

    @Test
    void testSavePredictionHappyFlow() {

        Prediction p = predictionRepository.save(prediction);
        assertNotNull(p);
        assertEquals(3, p.getHomeGoals());
        assertNull(p.getMatchDetails());

    }

    @Test
    void testSavePredictionInvalidKey() {
       prediction.setUserName(null);
       predictionRepository.save(prediction);

    }


}