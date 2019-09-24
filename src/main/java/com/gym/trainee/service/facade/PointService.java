package com.gym.trainee.service.facade;

import com.gym.trainee.model.contract.Exercise;
import com.gym.trainee.model.entity.Score;
import com.gym.trainee.service.util.PointsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PointService {
    private final Logger logger = LoggerFactory.getLogger(PointService.class);

    public Score calculateScore(Exercise source) {
        long
                multiplier = PointsUtil.getMultiplicationFactor(source.getType()),
                caloriePoints = source.getCalories(),
                durationPoints = PointsUtil.getDurationPoints(source.getDuration());

        long points = multiplier * (caloriePoints + durationPoints);

        Score score = new Score(
                source.getUserId(),
                source.getId(),
                source.getType(),
                points,
                source.getStartTime()
        );

        logger.info(String.format("finished processing score: %d->%s", source.getId(), score.toString()));
        logger.info(String.format("publishing PointEarnedEvent: %s", score.toString()));
        return score;
    }
}
