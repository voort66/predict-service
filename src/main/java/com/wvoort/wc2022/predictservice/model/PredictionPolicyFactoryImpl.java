package com.wvoort.wc2022.predictservice.model;

public class PredictionPolicyFactoryImpl implements PredictionPolicyFactory{
    @Override
    public Policy createPredictionPolicy() {
        return new BeforeStartPolicy();
    }
}
