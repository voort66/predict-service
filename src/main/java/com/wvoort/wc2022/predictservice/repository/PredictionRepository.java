package com.wvoort.wc2022.predictservice.repository;

import com.wvoort.wc2022.predictservice.model.Prediction;
import com.wvoort.wc2022.predictservice.model.PredictionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PredictionRepository extends JpaRepository<Prediction, PredictionId> {

    List<Prediction> findByUserName(String userName);

}