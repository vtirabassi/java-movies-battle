package com.tirabassi.javamoviesbattle.controllers;

import com.tirabassi.javamoviesbattle.domain.entities.Movie;
import com.tirabassi.javamoviesbattle.domain.repositories.MovieRepository;
import com.tirabassi.javamoviesbattle.domain.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/v1/games")
public class GameController {

    @Autowired
    GameService gameService;

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    public void start(@RequestBody @Valid String login) {
        gameService.starter(login);
    }

    @PostMapping("/stop")
    @ResponseStatus(HttpStatus.OK)
    public void stop(@RequestBody @Valid String login) {
        gameService.stop(login);
    }
}
