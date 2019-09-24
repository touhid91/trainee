package com.gym.trainee.repository;

import com.gym.trainee.model.entity.Score;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PointsRepository implements Repository<Long, Score> {
    private final Map<Long, Score> userPointsMap;

    public PointsRepository() {
        this.userPointsMap = new ConcurrentHashMap<>();
    }

    @Override
    public Optional<Score> get(Long exerciseId) {
        var score = userPointsMap.get(exerciseId);
        if (score == null)
            return Optional.empty();
        return Optional.of(Score.of(score));
    }

    @Override
    public Score add(Score entity) {
        return userPointsMap.putIfAbsent(entity.getExerciseId(), entity);
    }

    @Override
    public Score update(Long id, Score entity) {
        return userPointsMap.put(entity.getExerciseId(), entity);
    }
}
