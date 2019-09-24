package com.gym.trainee.controller;

import com.gym.trainee.model.contract.Exercise;
import com.gym.trainee.service.facade.ExerciseService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotNull;

@Controller
@RequestMapping(value = "exercise")
@ResponseBody
public class ExerciseController {
    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public Mono<Exercise> insertExercise(@NotNull @RequestBody Exercise exercise) {
        return Mono.just(exerciseService.add(exercise));
    }


    @PutMapping("{exerciseId}")
    @ResponseStatus(value = HttpStatus.OK)
    public Mono<Exercise> updateExercise(
            @NotNull @PathVariable("exerciseId") long exerciseId,
            @NotNull @RequestBody Exercise exercise) {

        return Mono.just(exerciseService.update(exerciseId, exercise));
    }
}
