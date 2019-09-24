package com.gym.trainee.repository;

import com.gym.trainee.model.contract.Exercise;
import com.gym.trainee.service.util.SequenceUtil;
import org.springframework.stereotype.Component;
import reactor.util.function.Tuple3;
import reactor.util.function.Tuples;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Component
public class ExerciseRepository implements Repository<Long, Exercise> {
    private final SequenceUtil generator;
    private Map<Long, Exercise> userExerciseMap;

    public ExerciseRepository(SequenceUtil simpleLongIdGenerator) {
        this.userExerciseMap = new ConcurrentHashMap<>();
        this.generator = simpleLongIdGenerator;
    }

    public List<Long> getInRange(long userId, OffsetDateTime startsAt, long duration) {
        OffsetDateTime endsAt = startsAt.plusSeconds(duration);

        return userExerciseMap.values().parallelStream()
                .filter(e -> e.getUserId() == userId)
                .map(e -> Tuples.of(
                        e.getStartTime(),
                        e.getStartTime().plusSeconds(e.getDuration()),
                        e.getId()
                        )
                )
                .filter(t -> !(t.getT2().isBefore(startsAt.plusNanos(1L)) || t.getT1().isAfter(endsAt.minusNanos(1L))))
                .map(Tuple3::getT3)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Optional<Exercise> get(Long exerciseId) {
        return Optional.ofNullable(userExerciseMap.get(exerciseId));
    }

    @Override
    public Exercise add(Exercise exercise) {
        long exerciseId = generator.next();
        exercise.setId(exerciseId);
        userExerciseMap.put(exerciseId, exercise);

        return userExerciseMap.get(exerciseId);
    }

    @Override
    public Exercise update(Long exerciseId, @Valid Exercise exercise) {
        userExerciseMap.put(exerciseId, exercise);
        return userExerciseMap.get(exerciseId);
    }
}
