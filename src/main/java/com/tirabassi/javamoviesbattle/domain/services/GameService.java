package com.tirabassi.javamoviesbattle.domain.services;

import com.tirabassi.javamoviesbattle.domain.entities.Movie;
import com.tirabassi.javamoviesbattle.domain.models.GameModel;
import com.tirabassi.javamoviesbattle.domain.models.RoundModel;

import java.util.List;

public interface GameService {

    List<Movie> starter(String login);

    void stop(String login);

    RoundModel nextRound(GameModel gameModel);
}
