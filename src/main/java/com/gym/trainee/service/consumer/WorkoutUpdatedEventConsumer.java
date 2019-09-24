package com.gym.trainee.service.consumer;

import com.gym.trainee.model.contract.Exercise;
import com.gym.trainee.model.entity.Score;
import com.gym.trainee.model.event.PointEarnedEvent;
import com.gym.trainee.model.event.WorkoutUpdatedEvent;
import com.gym.trainee.service.facade.PointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class WorkoutUpdatedEventConsumer implements ApplicationListener<WorkoutUpdatedEvent> {
    private final PointService pointService;
    private final ApplicationEventPublisher publisher;
    private final Logger logger = LoggerFactory.getLogger(WorkoutEventConsumer.class);

    public WorkoutUpdatedEventConsumer(PointService pointService, ApplicationEventPublisher publisher) {
        this.pointService = pointService;
        this.publisher = publisher;
    }

    @Override
    public void onApplicationEvent(WorkoutUpdatedEvent workoutUpdatedEvent) {
        logger.info(String.format("consuming WorkoutUpdatedEvent: %s", workoutUpdatedEvent));
        Exercise source = (Exercise) workoutUpdatedEvent.getSource();
        Score score = pointService.calculateScore(source);
        publisher.publishEvent(new PointEarnedEvent(score));
    }
}
