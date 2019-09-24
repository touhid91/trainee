package com.gym.trainee.controller;

import com.gym.trainee.model.contract.RankingUser;
import com.gym.trainee.service.facade.RankingService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Controller
@RequestMapping(value = "ranking")
@ResponseBody
public class RankingController {
    private final RankingService service;

    public RankingController(RankingService service) {
        this.service = service;
    }

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public Flux<RankingUser> getRanking(
            @NotNull @Valid @RequestParam(value = "userIds") Set<Long> userIds) {
        return Flux.fromIterable(
                service.getRanks(userIds)
        );
    }
}
