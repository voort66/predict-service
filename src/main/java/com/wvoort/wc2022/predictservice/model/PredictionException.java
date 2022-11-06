package com.wvoort.wc2022.predictservice.model;

public class PredictionException extends Exception {
    public PredictionException(String message) {
        super(message);
    }

    public PredictionException(String message, Throwable t) {
        super(message, t);
    }
}
