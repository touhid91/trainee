package com.gym.trainee.service.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserService {
    private final Map<Long, Set<Long>> userExerciseMap;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService() {
        this.userExerciseMap = new ConcurrentHashMap<>();
    }

    Optional<Set<Long>> get(long id) {
        return Optional.ofNullable(userExerciseMap.get(id));
    }

    public void add(long userId, long exerciseId) {
        if (!userExerciseMap.containsKey(userId)) {
            userExerciseMap.put(userId, Set.of(exerciseId));
        } else {
            Set<Long> existing = userExerciseMap.get(userId);
            Set<Long> updated = new HashSet<>(existing);
            updated.add(exerciseId);
            userExerciseMap.put(userId, Set.copyOf(updated));
        }

        logger.info(String.format("adding to map: %d->%d", userId, exerciseId));
    }

    public void remove(long userId, long exerciseId) {
        if (!userExerciseMap.containsKey(userId))
            return;

        Set<Long> existing = userExerciseMap.get(userId);

        if (existing == null || existing.isEmpty())
            return;

        existing.remove(exerciseId);
        userExerciseMap.put(userId, existing);
    }
}
