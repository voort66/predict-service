package com.wvoort.wc2022.predictservice.controller;

import com.wvoort.wc2022.predictservice.dto.PredictionDto;
import com.wvoort.wc2022.predictservice.model.Prediction;
import com.wvoort.wc2022.predictservice.model.PredictionException;
import com.wvoort.wc2022.predictservice.services.PredictionService;
import com.wvoort.wc2022.predictservice.services.PredictionValidationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@Controller
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @Autowired
    private PredictionValidationService predictionValidationService;



    @GetMapping(value = "/predictions/create")
    public String getPredictions(Authentication authentication, Model model) {

        final PredictionDto predictionDto = new PredictionDto();

        List<Prediction> predictions = predictionService.getPredictions(authentication.getName());
        predictions.forEach(predictionDto::addPrediction);

        model.addAttribute("user", authentication.getName());
        model.addAttribute("predictionDto", predictionDto);

        return "predictions_create";

    }

    @PostMapping("/predictions/save")
    public String doCreatePredictions(Authentication authentication, @Valid @ModelAttribute PredictionDto predictionDto,
                                      BindingResult bindingResult,
                                      Model model) {

        try {
            List<PredictionValidationService.ErrorDetails> errorDetails = predictionValidationService.validatePredictions(predictionDto.getPredictions());
            updateBindingResult(bindingResult, errorDetails);
        } catch (PredictionException e) {
            bindingResult.addError(new ObjectError("global", e.getMessage()));
        }



        if(bindingResult.hasErrors()){
            updatePredictionsWithMatchDetails(((PredictionDto)model.getAttribute("predictionDto")).getPredictions());
            model.addAttribute("user", authentication.getName());
            return "predictions_create";
        }

        predictionService.createPredictions(predictionDto.getPredictions());


        return "redirect:/predictions/create";
    }

    private void updateBindingResult(BindingResult bindingResult, List<PredictionValidationService.ErrorDetails> errorDetails) {
       errorDetails.forEach(e -> bindingResult.addError(new ObjectError(e.getFieldName(), e.getErrorDescription())));
    }

    private void updatePredictionsWithMatchDetails(List<Prediction> predictions) {

        predictions.forEach(predictionService::updatePredictionWithMatchDetails);
    }


}
