package com.gym.trainee.model.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;
import java.util.Comparator;

@Validated
public class RankingUser {
    @JsonProperty(value = "userId")
    private long userId;

    @JsonProperty(value = "points")
    private float points;

    @JsonIgnore()
    private OffsetDateTime lastActiveAt;

    public RankingUser(long userId){
        this.userId = userId;
        this.points = 0f;
    }

    public RankingUser(long userId, Float points, OffsetDateTime lastActiveAt) {
        this.userId = userId;
        this.points = points;
        this.lastActiveAt = lastActiveAt;
    }

    public OffsetDateTime getLastActiveAt() {
        return lastActiveAt;
    }

    public float getPoints() {
        return points;
    }
}
