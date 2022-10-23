package com.tirabassi.javamoviesbattle.domain.services.impl;

import com.tirabassi.javamoviesbattle.domain.entities.Movie;
import com.tirabassi.javamoviesbattle.domain.entities.Rank;
import com.tirabassi.javamoviesbattle.domain.models.GameModel;
import com.tirabassi.javamoviesbattle.domain.models.RankModel;
import com.tirabassi.javamoviesbattle.domain.repositories.MovieRepository;
import com.tirabassi.javamoviesbattle.domain.repositories.RankRepository;
import com.tirabassi.javamoviesbattle.domain.repositories.UserRepository;
import com.tirabassi.javamoviesbattle.domain.services.GameService;
import com.tirabassi.javamoviesbattle.exceptions.BusinessException;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    RankRepository rankRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieRepository movieRepository;

    private List<Movie> MOVIES;
    private List<Movie> MOVIES_ACTUAL_ROUND;
    private Integer ERRORS = 0;

    @Transactional
    @Override
    public List<Movie> starter(String login) {

        var user = userRepository.findByLogin(login)
                .orElseThrow(() -> new BusinessException("Not Found"));

        var rank = rankRepository.findByLogin(login)
                .orElse(new Rank());

        if (rank.isInGame())
            throw new BusinessException("Player already in game");

        rank.setUser(user);
        rank.setInGame(true);
        rank.setGamesPlayed(rank.getGamesPlayed() + 1);
        rankRepository.save(rank);

        MOVIES = movieRepository.findAll();
        MOVIES_ACTUAL_ROUND = getOnlyTwoMovies();

        return MOVIES_ACTUAL_ROUND;
    }

    @Transactional
    @Override
    public void stop(String login) {
        var rank = rankRepository.findByLogin(login)
                .orElseThrow(() -> new BusinessException("Not Found"));

        if (!rank.isInGame())
            throw new BusinessException("Player is not in game");

        MOVIES.clear();
        MOVIES_ACTUAL_ROUND.clear();
        ERRORS = 0;

        rank.setInGame(false);
        rankRepository.save(rank);
    }

    @Override
    public List<Movie> nextRound(GameModel gameModel) {

        var movieWinner = MOVIES_ACTUAL_ROUND
                .stream()
                .sorted(Comparator.comparing(Movie::getRating).reversed())
                .collect(Collectors.toList())
                .get(0);

        if(movieWinner.getTitle().equals(gameModel.getTitle()))
        {
            var rank = rankRepository.findByLogin(gameModel.getLogin())
                    .orElse(new Rank());

            rank.setAnswerCorrects(rank.getAnswerCorrects() + 1);
            rankRepository.save(rank);
        }
        else {
            if (ERRORS < 3)
                ERRORS++;
            else{
                stop(gameModel.getLogin());
                throw new BusinessException("You reached the maximum number of error allowed");
            }
        }

        return getOnlyTwoMovies();
    }

    private List<Movie> getOnlyTwoMovies(){

        var numberOfInterations = 2;
        var onlyTwoMovies = new ArrayList<Movie>();

        for (int i = 0; i < numberOfInterations; i++) {
            var randomIndex = new Random().nextInt(MOVIES.size());
            onlyTwoMovies.add(MOVIES.get(randomIndex));
            MOVIES.remove(randomIndex);
        }

        return onlyTwoMovies;
    }
}
