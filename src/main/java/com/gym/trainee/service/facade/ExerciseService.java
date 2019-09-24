package com.gym.trainee.service.facade;

import com.gym.trainee.model.contract.Exercise;
import com.gym.trainee.model.event.WorkoutEvent;
import com.gym.trainee.model.event.WorkoutUpdatedEvent;
import com.gym.trainee.repository.ExerciseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Optional;

@Service
@Validated
public class ExerciseService {
    private final ExerciseRepository repository;
    private final ApplicationEventPublisher publisher;
    private final Logger logger = LoggerFactory.getLogger(ExerciseService.class);

    public ExerciseService(
            ExerciseRepository repository,
            ApplicationEventPublisher publisher
    ) {
        this.repository = repository;
        this.publisher = publisher;
    }

    public Exercise add(@Valid Exercise exercise) {
        var elementInRange = repository.getInRange(
                exercise.getUserId(), exercise.getStartTime(), exercise.getDuration()
        );

        if (!elementInRange.isEmpty()) {
            logger.error(String.format("conflict: %d", elementInRange.get(0)));
            throw new IllegalArgumentException(String.format(
                    "exercise '%d' exist in the provided time range", elementInRange.get(0)
            ));
        }

        Exercise updated = repository.add(exercise);

        logger.info(String.format("publishing WorkoutEvent: %s", updated.toString()));
        publisher.publishEvent(new WorkoutEvent(updated));

        return updated;
    }

    public Exercise update(long exerciseId, @Valid Exercise exercise) {
        if (exerciseId != exercise.getId()) {
            logger.error(String.format("mismatch: %s%d", exercise.toString(), exerciseId));
            throw new IllegalArgumentException(String.format(
                    "body parameter '%d' does not match request for exercise '%d'", exercise.getId(), exerciseId)
            );
        }

        Optional<Exercise> candidate = repository.get(exerciseId);
        if (candidate.isEmpty()) {
            logger.error("not found: " + exerciseId);
            throw new IndexOutOfBoundsException(String.format("target exerciseId %d does not exist", exerciseId));
        }

        Exercise e = candidate.get();

        if (e.getUserId() != exercise.getUserId()) {
            logger.error("invalid operation: " + exercise.getUserId());
            throw new IllegalArgumentException("changing userId is not permitted");
        }

        if (e.getType() != exercise.getType()) {
            logger.error("invalid operation: " + exercise.getType().toString());
            throw new IllegalArgumentException("changing type is not permitted");
        }

        var elementInRange = repository.getInRange(
                exercise.getUserId(), exercise.getStartTime(), exercise.getDuration()
        );

        if (!elementInRange.isEmpty()) {
            var occupant = elementInRange.stream().filter(e1 -> e1 != exerciseId).findFirst();

            if (occupant.isPresent()) {
                logger.error("range pre occupied: " + exercise.getType().toString());
                throw new IllegalArgumentException(String.format("exercise '%d' exist in the provided time range",
                        occupant.get()
                ));
            }
        }

        e.setDescription(exercise.getDescription());
        e.setCalories(exercise.getCalories());
        e.setDuration(exercise.getDuration());
        e.setStartTime(exercise.getStartTime());

        logger.info(String.format("publishing WorkoutUpdatedEvent: %s", e.toString()));
        publisher.publishEvent(new WorkoutUpdatedEvent(e));

        return e;
    }
}
