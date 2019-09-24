package com.gym.trainee.model.error;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Violation {

    private final String fieldName;
    private final String message;

    @JsonProperty(value = "field")
    public String getFieldName() {
        return fieldName;
    }

    @JsonProperty(value = "message")
    public String getMessage() {
        return message;
    }

    public Violation(String fieldName, String message) {
        this.fieldName = fieldName;
        this.message = message;
    }

    @Override
    public String toString() {
        return "Violation{" +
                "fieldName='" + fieldName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
