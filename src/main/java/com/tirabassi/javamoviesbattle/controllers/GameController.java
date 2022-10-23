package com.tirabassi.javamoviesbattle.controllers;

import com.tirabassi.javamoviesbattle.domain.entities.Movie;
import com.tirabassi.javamoviesbattle.domain.mappers.MovieMapper;
import com.tirabassi.javamoviesbattle.domain.mappers.RankMapper;
import com.tirabassi.javamoviesbattle.domain.models.GameModel;
import com.tirabassi.javamoviesbattle.domain.models.MovieModel;
import com.tirabassi.javamoviesbattle.domain.repositories.MovieRepository;
import com.tirabassi.javamoviesbattle.domain.services.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/games")
public class GameController {

    @Autowired
    GameService gameService;

    @PostMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    public List<MovieModel> start(@RequestBody @Valid GameModel gameModel) {

        var movies = gameService.starter(gameModel.getLogin());

        return movies.stream()
                .map(movie -> MovieMapper.toModel(movie))
                .collect(Collectors.toList());
    }

    @PostMapping("/stop")
    @ResponseStatus(HttpStatus.OK)
    public void stop(@RequestBody @Valid GameModel gameModel) {
        gameService.stop(gameModel.getLogin());
    }

    @PostMapping("/rounds")
    @ResponseStatus(HttpStatus.OK)
    public List<MovieModel> nextRound(@RequestBody @Valid GameModel gameModel) {
        var movies = gameService.nextRound(gameModel);

        return movies.stream()
                .map(movie -> MovieMapper.toModel(movie))
                .collect(Collectors.toList());
    }
}
