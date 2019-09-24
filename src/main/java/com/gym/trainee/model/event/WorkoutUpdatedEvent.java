package com.gym.trainee.model.event;

import com.gym.trainee.model.contract.Exercise;
import org.springframework.context.ApplicationEvent;

public class WorkoutUpdatedEvent extends ApplicationEvent {
    public WorkoutUpdatedEvent(Exercise source) {
        super(source);
    }
}
