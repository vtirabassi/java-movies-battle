package com.tirabassi.javamoviesbattle.domain.services.impl;

import com.google.common.base.Strings;
import com.tirabassi.javamoviesbattle.domain.entities.Movie;
import com.tirabassi.javamoviesbattle.domain.entities.Rank;
import com.tirabassi.javamoviesbattle.domain.mappers.MovieMapper;
import com.tirabassi.javamoviesbattle.domain.models.GameModel;
import com.tirabassi.javamoviesbattle.domain.models.RoundModel;
import com.tirabassi.javamoviesbattle.domain.repositories.MovieRepository;
import com.tirabassi.javamoviesbattle.domain.repositories.RankRepository;
import com.tirabassi.javamoviesbattle.domain.repositories.UserRepository;
import com.tirabassi.javamoviesbattle.domain.services.GameService;
import com.tirabassi.javamoviesbattle.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return getOnlyTwoMovies();
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

    @Transactional
    @Override
    public RoundModel nextRound(GameModel gameModel) {

        var validate = validateRound(gameModel);
        if (!Objects.isNull(validate))
            return validate;

        var message = processUserChoice(gameModel);

        var nextMovies = getOnlyTwoMovies()
                .stream()
                .map(movie -> MovieMapper.toModel(movie))
                .collect(Collectors.toList());

        return new RoundModel(message, nextMovies);
    }

    private RoundModel validateRound(GameModel gameModel){

        if(Strings.isNullOrEmpty(gameModel.getTitle()))
        {
            var actualRound = MOVIES_ACTUAL_ROUND.stream()
                    .map(movie -> MovieMapper.toModel(movie))
                    .collect(Collectors.toList());

            return new RoundModel("You have to choose a movie", actualRound);
        }

        if (MOVIES.stream().count() < 2)
        {
            stop(gameModel.getLogin());
            return new RoundModel("Congratulations! You are finished the game", null);
        }

        return null;
    }

    private String processUserChoice(GameModel gameModel){

        var rank = rankRepository.findByLogin(gameModel.getLogin())
                .orElse(new Rank());

        if (!rank.isInGame())
            throw new BusinessException("Player is not in game, please start the game");

        var movieWinner = MOVIES_ACTUAL_ROUND
                .stream()
                .sorted(Comparator.comparing(Movie::getRating).reversed())
                .collect(Collectors.toList())
                .get(0);

        if(movieWinner.getTitle().equals(gameModel.getTitle()))
        {
            rank.setAnswerCorrects(rank.getAnswerCorrects() + 1);
            rankRepository.save(rank);
            return "Nice choice";
        }
        else {
            if (ERRORS < 3)
            {
                ERRORS++;
                return String.format("Take care, you have more %s chances",
                        (3 - ERRORS == 0) ? 1 : 3 - ERRORS);
            }
            else{
                stop(gameModel.getLogin());
                throw new BusinessException("You reached the maximum number of error allowed");
            }
        }
    }

    private List<Movie> getOnlyTwoMovies(){
        var numberOfInterations = 2;
        var onlyTwoMovies = new ArrayList<Movie>();

        for (int i = 0; i < numberOfInterations; i++) {
            var randomIndex = new Random().nextInt(MOVIES.size());
            onlyTwoMovies.add(MOVIES.get(randomIndex));
            MOVIES.remove(randomIndex);
        }

        MOVIES_ACTUAL_ROUND = onlyTwoMovies;
        return MOVIES_ACTUAL_ROUND;
    }
}
