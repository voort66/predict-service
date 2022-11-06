package com.wvoort.wc2022.predictservice.controller;


import com.wvoort.wc2022.predictservice.dto.PredictionDto;
import com.wvoort.wc2022.predictservice.model.Prediction;
import com.wvoort.wc2022.predictservice.model.PredictionException;
import com.wvoort.wc2022.predictservice.services.PredictionService;
import com.wvoort.wc2022.predictservice.services.PredictionValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PredictionRestController {

    @Autowired
    private PredictionService predictionService;

    @GetMapping(value = "/predictions/all")
    public List<Prediction> getPredictions(Authentication authentication) {
        return predictionService.getAllPredictions(authentication.getName());
    }

    @GetMapping(value = "/predictions/editable")
    public List<Prediction> getEditablePredictions(Authentication authentication) {
        return predictionService.getEditablePredictions(authentication.getName());
    }

    @PostMapping(value = "/predictions/save")
    public ResponseEntity<PredictionDto> doCreatePredictions(Authentication authentication,
                                                             @Valid @ModelAttribute PredictionDto predictionDto) {

//        try {
//            List<PredictionValidationService.ErrorDetails> errorDetails = predictionValidationService.validatePredictions(predictionDto.getPredictions());
//            updateBindingResult(bindingResult, errorDetails);
//        } catch (PredictionException e) {
//            bindingResult.addError(new ObjectError("global", e.getMessage()));
//        }


//        if (bindingResult.hasErrors()) {
//            updatePredictionsWithMatchDetails(((PredictionDto) model.getAttribute("predictionDto")).getPredictions());
//            model.addAttribute("user", authentication.getName());
//            return "predictions_create";
//        }

        PredictionDto  newPredictionDto = new PredictionDto();
        newPredictionDto.setPredictions(predictionService.createPredictions(predictionDto.getPredictions()));

        return new ResponseEntity<>(newPredictionDto, HttpStatus.CREATED);
    }
}
