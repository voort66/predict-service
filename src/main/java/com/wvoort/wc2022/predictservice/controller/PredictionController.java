package com.wvoort.wc2022.predictservice.controller;

import com.wvoort.wc2022.predictservice.dto.PredictionDto;
import com.wvoort.wc2022.predictservice.model.Prediction;
import com.wvoort.wc2022.predictservice.services.PredictionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j
@Controller
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @GetMapping(value = "/predictions/create")
    public String getPredictions(Authentication authentication, Model model) {

        final PredictionDto predictionDto = new PredictionDto();
        predictionDto.setUser(authentication.getName());
        List<Prediction> predictions = predictionService.getPredictions(authentication.getName());
        predictions.forEach(predictionDto::addPrediction);

        model.addAttribute("predictionDtoObject", predictionDto);

        return "predictions_create";

    }

    @PostMapping("/predictions/save")
    public String doCreatePredictions(Authentication authentication, BindingResult bindingResult,
                                      @Valid @ModelAttribute PredictionDto predictionDtoObject,
                                      Model model) {

        if(bindingResult.hasErrors()){
            return "/predictions/create";
        }

        predictionService.createPredictions(predictionDtoObject.getPredictions());

        model.addAttribute("predictionDtoObject", predictionDtoObject);
        return "/predictions/create";
    }




}
