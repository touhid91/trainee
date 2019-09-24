package com.gym.trainee.model.entity;

import com.gym.trainee.model.contract.ExerciseType;

import java.time.OffsetDateTime;

public class Score implements Comparable<Score> {
    private long userId;
    private long exerciseId;
    private ExerciseType exerciseType;
    private float points;
    private OffsetDateTime datedAt;

    public Score(long userId, long exerciseId, ExerciseType exerciseType, float points, OffsetDateTime datedAt) {
        this.userId = userId;
        this.exerciseId = exerciseId;
        this.exerciseType = exerciseType;
        this.points = points;
        this.datedAt = datedAt;
    }

    public static Score of(Score score) {
        return new Score(score.userId, score.exerciseId, score.exerciseType, score.points, score.datedAt);
    }

    @Override
    public String toString() {
        return "Score{" +
                "userId=" + userId +
                ", exerciseId=" + exerciseId +
                ", exerciseType=" + exerciseType +
                ", points=" + points +
                ", datedAt=" + datedAt +
                '}';
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public ExerciseType getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(ExerciseType exerciseType) {
        this.exerciseType = exerciseType;
    }

    public float getPoints() {
        return points;
    }

    public void setPoints(float points) {
        this.points = points;
    }

    public OffsetDateTime getDatedAt() {
        return datedAt;
    }

    public void setDatedAt(OffsetDateTime datedAt) {
        this.datedAt = datedAt;
    }

    @Override
    public int compareTo(Score score) {
        return getDatedAt().compareTo(score.getDatedAt());
    }
}
