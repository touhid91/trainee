package com.gym.trainee.service.consumer;

import com.gym.trainee.model.contract.Exercise;
import com.gym.trainee.model.entity.Score;
import com.gym.trainee.model.event.PointEarnedEvent;
import com.gym.trainee.model.event.WorkoutEvent;
import com.gym.trainee.service.facade.PointService;
import com.gym.trainee.service.facade.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class WorkoutEventConsumer implements ApplicationListener<WorkoutEvent> {
    private final PointService pointService;
    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final Logger logger = LoggerFactory.getLogger(WorkoutEventConsumer.class);

    public WorkoutEventConsumer(PointService pointService, UserService userService, ApplicationEventPublisher publisher) {
        this.pointService = pointService;
        this.userService = userService;
        this.publisher = publisher;
    }

    @Override
    public void onApplicationEvent(WorkoutEvent exerciseAddedEvent) {
        logger.info(String.format("consuming WorkoutEvent: %s", exerciseAddedEvent));
        Exercise source = (Exercise) exerciseAddedEvent.getSource();
        Score score = pointService.calculateScore(source);
        userService.add(source.getUserId(), source.getId());
        publisher.publishEvent(new PointEarnedEvent(score));
    }
}
