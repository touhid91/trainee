package com.gym.trainee.service.facade;

import com.gym.trainee.model.contract.RankingUser;
import com.gym.trainee.model.entity.Score;
import com.gym.trainee.repository.Repository;
import com.gym.trainee.service.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Validated
public class RankingService {
    private final Repository<Long, Score> pointsRepository;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(RankingService.class);

    public RankingService(Repository<Long, Score> repository, UserService userService) {
        this.pointsRepository = repository;
        this.userService = userService;
    }

    public List<RankingUser> getRanks(@Valid Set<Long> userIds) {
        List<Long> exercises = new LinkedList<>();
        List<Score> points = new ArrayList<>();
        Map<Tuple2<Long, Integer>, Integer> frequencies = new HashMap<>();
        List<RankingUser> users = new LinkedList<>();

        for (long userId : userIds)
            exercises.addAll(userService.get(userId).orElse(new HashSet<>()));

        logger.info(String.format("processing exercises for user: %s , events: %d", userIds, exercises.size()));

        OffsetDateTime compareTo = DateUtil.now();
        for (long exerciseId : exercises) {
            Score score = pointsRepository.get(exerciseId).orElse(null);

            logger.info(String.format("exercise: %d score: %s", exerciseId, score));

            if (score == null) continue;
            OffsetDateTime datedAt = score.getDatedAt();
            if (datedAt.isBefore(compareTo.minusDays(28).minusNanos(1)) || datedAt.isAfter(compareTo.minusNanos(1))) {
                logger.info(String.format("skipping exercise %s", score));
                continue;
            }

            points.add(score);
        }

        Collections.sort(points);

        for (Score p : points) {
            Tuple2<Long, Integer> key = Tuples.of(p.getUserId(), p.getExerciseType().intValue());

            if (!frequencies.containsKey(key)) frequencies.put(key, 1);
            else frequencies.put(key, 1 + frequencies.get(key));

            int percent = 100 - 10 * (frequencies.get(key) - 1);
            p.setPoints(Math.max(0, p.getPoints() * percent / 100));
        }

        logger.debug(String.format("frequencies: %s", frequencies.toString()));

        for (long userId : userIds) {
            float userPoints = ((Double) points.parallelStream()
                    .filter(p -> p.getUserId() == userId)
                    .map(p -> (double) p.getPoints()).mapToDouble(Double::doubleValue).sum())
                    .floatValue();

            Optional<OffsetDateTime> lastActiveAt = points.parallelStream()
                    .filter(p -> p.getUserId() == userId)
                    .map(Score::getDatedAt)
                    .max(OffsetDateTime::compareTo);

            logger.info(String.format("points aggregated: points: %s lastActiveAt: %s", userPoints, lastActiveAt));
            users.add(new RankingUser(userId, userPoints, lastActiveAt.orElse(compareTo.minusDays(28))));
        }

        Comparator<RankingUser> comparator = Comparator.comparing(RankingUser::getPoints)
                .reversed()
                .thenComparing(RankingUser::getLastActiveAt, Comparator.reverseOrder());

        return users.stream()
                .sorted(comparator)
                .collect(Collectors.toUnmodifiableList());
    }


    public void updateScore(long exerciseId, Score score) {
        pointsRepository.update(exerciseId, score);
    }
}
