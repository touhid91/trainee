package com.gym.trainee.service.consumer;

import com.gym.trainee.model.entity.Score;
import com.gym.trainee.model.event.PointEarnedEvent;
import com.gym.trainee.service.facade.RankingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class PointEarnedEventConsumer implements ApplicationListener<PointEarnedEvent> {
    private final RankingService service;
    private final Logger logger = LoggerFactory.getLogger(PointEarnedEventConsumer.class);

    public PointEarnedEventConsumer(RankingService service) {
        this.service = service;
    }

    @Override
    public void onApplicationEvent(PointEarnedEvent event) {
        logger.info(String.format("consuming PointEarnedEvent: %s", event));
        Score score = (Score) event.getSource();
        service.updateScore(score.getExerciseId(), score);
    }
}
