package com.wvoort.wc2022.predictservice.model;

public interface Policy {

    void apply() throws PredictionException;
}
