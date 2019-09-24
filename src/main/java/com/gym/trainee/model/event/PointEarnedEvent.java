package com.gym.trainee.model.event;

import com.gym.trainee.model.entity.Score;
import org.springframework.context.ApplicationEvent;

public class PointEarnedEvent extends ApplicationEvent {
    public PointEarnedEvent(Score source) {
        super(source);
    }
}
