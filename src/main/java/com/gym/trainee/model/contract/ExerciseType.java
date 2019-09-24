package com.gym.trainee.model.contract;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ExerciseType {
    NONE(""),
    RUNNING("RUNNING"),
    SWIMMING("SWIMMING"),
    STRENGTH_TRAINING("STRENGTH_TRAINING"),
    CIRCUIT_TRAINING("CIRCUIT_TRAINING");

    private String value;

    ExerciseType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static ExerciseType fromValue(String text) {
        for (ExerciseType b : ExerciseType.values()) {
            if (String.valueOf(b.value).equals(text)) {
                return b;
            }
        }
        return null;
    }

    @Override
    @JsonValue
    public String toString() {
        return String.valueOf(value);
    }

    public int intValue() {
        switch (this) {
            default:
            case NONE:
                return 0;
            case RUNNING:
                return 1;
            case SWIMMING:
                return 2;
            case CIRCUIT_TRAINING:
                return 3;
            case STRENGTH_TRAINING:
                return 4;
        }
    }
}