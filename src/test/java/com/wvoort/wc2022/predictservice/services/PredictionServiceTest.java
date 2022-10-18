package com.wvoort.wc2022.predictservice.services;

import com.wvoort.wc2022.predictservice.model.Matches;
import com.wvoort.wc2022.predictservice.model.Prediction;
import com.wvoort.wc2022.predictservice.repository.PredictionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PredictionService.class })
class PredictionServiceTest {

    @Autowired
    private PredictionService predictionService;

    @MockBean
    private MatchService matchService;

    @MockBean
    private PredictionRepository predictionRepository;



    @BeforeEach
    private void setUp() throws IOException {
        String jsonString = Files.readString(Paths.get("build/resources/test/matches.json"));
        Matches matches = Matches.fromJsonResponseString(jsonString);

        when(matchService.getMatches()).thenReturn(matches);

        Prediction p1 = new Prediction();
        p1.setMatchId(855734L);
        p1.setUserName("Wim");
        p1.setAwayGoals(6);
        p1.setHomeGoals(1);

        Prediction p2 = new Prediction();
        p2.setMatchId(855735L);
        p2.setUserName("Wim");
        p2.setAwayGoals(2);
        p2.setHomeGoals(0);

        List<Prediction> predictions = new ArrayList<>();
        predictions.add(p1);
        predictions.add(p2);
        when(predictionRepository.findByUserName(anyString())).thenReturn(predictions);


    }

    @Test
    void testCompleteSetOfPredictions() {
        List<Prediction> predictions = predictionService.getPredictions("Wim");
        assertNotNull(predictions);
        assertEquals(48, predictions.size());

        assertEquals(2, predictions.stream().filter(p -> p.getAwayGoals() != null).count());
        assertEquals( 48,  predictions.stream().filter(p -> p.getMatchDetails() != null).count());
    }




}