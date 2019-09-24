package com.gym.trainee.service.util;

import com.gym.trainee.model.contract.ExerciseType;

public class PointsUtil {
    public static long getDurationPoints(Long duration) {
        return Double.valueOf(Math.ceil(duration / 60f)).longValue();
    }

    public static long getMultiplicationFactor(ExerciseType type) {
        long multiplier = 0L;
        switch (type) {
            case RUNNING:
                multiplier = 2L;
                break;

            case SWIMMING:
            case STRENGTH_TRAINING:
                multiplier = 3L;
                break;

            case CIRCUIT_TRAINING:
                multiplier = 4L;
                break;
        }

        return multiplier;
    }
}
