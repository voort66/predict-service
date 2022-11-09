package com.wvoort.wc2022.predictservice.controller;


import com.google.gson.GsonBuilder;
import com.wvoort.wc2022.predictservice.PredictionConstants;
import com.wvoort.wc2022.predictservice.model.Predictions;
import com.wvoort.wc2022.predictservice.services.PredictionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

@Slf4j
@RestController
public class PredictionRestController {

    @Autowired
    private PredictionService predictionService;

    @GetMapping(value = "/predictions/all")
    @RolesAllowed("ROLE_" + PredictionConstants.RW)
    public Predictions getPredictions(Authentication authentication) {
        return predictionService.getAllPredictions(authentication.getName());
    }

    @GetMapping(value = "/predictions/editable")
    @RolesAllowed("ROLE_" + PredictionConstants.RW)
    public Predictions getEditablePredictions(Authentication authentication) {
        return predictionService.getEditablePredictions(authentication.getName());
    }

    @GetMapping(value = "/predictions/users")
    @RolesAllowed("ROLE_" + PredictionConstants.READ_ALL_USERS_AUTHORITY)
    public Predictions getPredictionsForAllUsers(Authentication authentication) {
        return predictionService.getPredictionsForAllUsers();
    }

    @PostMapping(value = "/predictions/save", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    @RolesAllowed("ROLE_" + PredictionConstants.RW)
    public ResponseEntity<String> doCreatePredictions(Authentication authentication,
                                                      @Valid @RequestBody Predictions predictionDto) {


        try {
            Predictions savedPredictions = predictionService.createPredictions(predictionDto);

            return new ResponseEntity<>(Predictions.toJson(savedPredictions), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);


    }
}
