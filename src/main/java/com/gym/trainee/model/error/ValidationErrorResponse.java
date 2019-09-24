package com.gym.trainee.model.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class ValidationErrorResponse {
    private List<Violation> violations = new ArrayList<>();

    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss'Z'", timezone = "UTC")
    private final OffsetDateTime time = OffsetDateTime.now(ZoneId.of("UTC"));

    @Override
    public String toString() {
        return "ValidationErrorResponse{" +
                "violations=" + violations +
                ", time=" + time +
                '}';
    }

    @JsonProperty(value = "errors")
    public List<Violation> getViolations() {
        return violations;
    }

    public void setViolations(List<Violation> violations) {
        this.violations = violations;
    }
}
