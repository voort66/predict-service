package com.wvoort.wc2022.predictservice.controller;


import com.wvoort.wc2022.predictservice.model.Prediction;
import com.wvoort.wc2022.predictservice.services.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PredictionRestController {

    @Autowired
    private PredictionService predictionService;

    @GetMapping(value = "/predictions")
    public List<Prediction> getPredictions(Authentication authentication) {
        return predictionService.getPredictions(authentication.getName());
    }
}
