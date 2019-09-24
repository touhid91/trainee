package com.gym.trainee.model.contract;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Exercise {
    @JsonProperty(value = "id")
    private long id;

    @NotNull
    @JsonProperty(value = "userId")
    private long userId;

    @NotNull
    @JsonProperty(value = "description")
    private String description;

    @NotNull
    @JsonProperty(value = "type")
    private ExerciseType type;

    @NotNull
    @JsonProperty(value = "startTime")
    @Pattern(message = "must be a valid ISO 8601 date", regexp = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z")
    private String startTime;

    @NotNull
    @JsonProperty(value = "duration")
    private Long duration;

    @NotNull
    @JsonProperty(value = "calories")
    private long calories;

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExerciseType getType() {
        return type;
    }

    public void setType(ExerciseType type) {
        this.type = type;
    }

    public OffsetDateTime getStartTime() {
        return OffsetDateTime.parse(startTime).atZoneSameInstant(ZoneId.of("UTC")).toOffsetDateTime();
    }

    public void setStartTime(OffsetDateTime startTime) {
        this.startTime = startTime.atZoneSameInstant(ZoneId.of("UTC")).format(DateTimeFormatter.ISO_INSTANT);
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public long getCalories() {
        return calories;
    }

    public void setCalories(Long calories) {
        this.calories = calories;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "id=" + id +
                ", userId=" + userId +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", startTime='" + startTime + '\'' +
                ", duration=" + duration +
                ", calories=" + calories +
                '}';
    }
}