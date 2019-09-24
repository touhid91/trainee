package com.gym.trainee.model.event;

import com.gym.trainee.model.contract.Exercise;
import org.springframework.context.ApplicationEvent;

public class WorkoutEvent extends ApplicationEvent {
    public WorkoutEvent(Exercise source) {
        super(source);
    }
}
