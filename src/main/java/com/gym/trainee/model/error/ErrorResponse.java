package com.gym.trainee.model.error;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ErrorResponse {
    @JsonProperty("timestamp")
    private String dateTime;
    @JsonProperty("status")
    private int status;
    @JsonProperty("error")
    private String error;
    @JsonProperty("message")
    private Object message;

    public ErrorResponse(HttpStatus status, Object message) {
        this.dateTime = OffsetDateTime.now().atZoneSameInstant(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_INSTANT);
        this.status = status.value();
        this.error = status.getReasonPhrase();
        this.message = message;
    }
}