package com.tirabassi.javamoviesbattle.domain.services;

import com.github.javafaker.Faker;
import com.tirabassi.javamoviesbattle.Init.ModelInit;
import com.tirabassi.javamoviesbattle.Init.RepositoryInit;
import com.tirabassi.javamoviesbattle.domain.entities.Movie;
import com.tirabassi.javamoviesbattle.domain.entities.Rank;
import com.tirabassi.javamoviesbattle.domain.entities.User;
import com.tirabassi.javamoviesbattle.domain.repositories.MovieRepository;
import com.tirabassi.javamoviesbattle.domain.repositories.RankRepository;
import com.tirabassi.javamoviesbattle.domain.repositories.UserRepository;
import com.tirabassi.javamoviesbattle.domain.services.impl.GameServiceImpl;
import com.tirabassi.javamoviesbattle.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class GameServiceImplTest {

    @InjectMocks
    GameServiceImpl gameService;

    @Mock
    RankRepository rankRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    MovieRepository movieRepository;

    @Mock
    private List<Movie> MOVIES;

    @Mock
    private List<Movie> MOVIES_ACTUAL_ROUND;

    private Faker faker;

    @BeforeEach
    public void init(){
        faker = new Faker();
        initMocks(this);
    }

    @Test
    public void starterWithSuccess() {

        var name = faker.name().firstName();

        var userResponse = RepositoryInit.getUserValid(name);
        var rankResponse = RepositoryInit.getRankValid(name, false);
        var movieResponse = RepositoryInit.getMovies();

        when(userRepository.findByLogin(name)).thenReturn(userResponse);
        when(rankRepository.findByLogin(name)).thenReturn(rankResponse);
        when(rankRepository.save(Mockito.any(Rank.class))).thenReturn(rankResponse.get());

        when(movieRepository.findAll()).thenReturn(movieResponse);

        var movies = gameService.starter(name);

        assertEquals(movies.size(), 2);
        assertEquals(movieResponse.size(), 0);
    }

    @Test
    public void starterInGame() {

        var name = faker.name().firstName();

        var userResponse = RepositoryInit.getUserValid(name);
        var rankResponse = RepositoryInit.getRankValid(name, true);
        var movieResponse = RepositoryInit.getMovies();

        when(userRepository.findByLogin(name)).thenReturn(userResponse);
        when(rankRepository.findByLogin(name)).thenReturn(rankResponse);
        when(rankRepository.save(Mockito.any(Rank.class))).thenReturn(rankResponse.get());

        when(movieRepository.findAll()).thenReturn(movieResponse);

        Throwable exception = assertThrows(BusinessException.class, () -> gameService.starter(name));

        assertEquals("Player already in game", exception.getMessage());
    }

    @Test
    public void starterNotFound() {

        var name = faker.name().firstName();

        Optional<User> userResponse = Optional.empty();

        when(userRepository.findByLogin(name)).thenReturn(userResponse);

        Throwable exception = assertThrows(BusinessException.class, () -> gameService.starter(name));

        assertEquals("Not Found user", exception.getMessage());
    }

    @Test
    public void stopWithSuccess() {

        var name = faker.name().firstName();

        var rankResponse = RepositoryInit.getRankValid(name, true);

        when(rankRepository.findByLogin(name)).thenReturn(rankResponse);
        when(rankRepository.save(Mockito.any(Rank.class))).thenReturn(rankResponse.get());

        gameService.stop(name);

        assertEquals(rankResponse.get().isInGame(), false);
    }

    @Test
    public void stopWithSuccessNotFound() {

        var name = faker.name().firstName();

        Optional<Rank> rankResponse = Optional.empty();

        when(rankRepository.findByLogin(name)).thenReturn(rankResponse);

        Throwable exception = assertThrows(BusinessException.class, () -> gameService.stop(name));

        assertEquals("Not Found rank", exception.getMessage());
    }

    @Test
    public void stopWithSuccessNotInGame() {

        var name = faker.name().firstName();

        var rankResponse = RepositoryInit.getRankValid(name, false);

        when(rankRepository.findByLogin(name)).thenReturn(rankResponse);

        Throwable exception = assertThrows(BusinessException.class, () -> gameService.stop(name));

        assertEquals("Player is not in game", exception.getMessage());
    }

    @Test
    public void nextRoundWithSuccessTitleCorret() {

        var gameModel = ModelInit.getGameModelValid();

        var rankResponse = RepositoryInit.getRankValid(gameModel.getLogin(), true);
        var movieResponse = RepositoryInit.getMovies();
        gameModel.setTitle(movieResponse.get(0).getTitle());

        ReflectionTestUtils.setField(gameService, "MOVIES", movieResponse);
        ReflectionTestUtils.setField(gameService, "MOVIES_ACTUAL_ROUND", movieResponse);

        when(rankRepository.findByLogin(gameModel.getLogin())).thenReturn(rankResponse);
        when(rankRepository.save(Mockito.any(Rank.class))).thenReturn(rankResponse.get());

        when(movieRepository.findAll()).thenReturn(movieResponse);

        var rank = gameService.nextRound(gameModel);

        assertEquals(rank.getMessage(), "Nice choice");
        assertEquals(rank.getMovies().size(), 2);
    }

    @Test
    public void nextRoundWithSuccessFinishGame() {

        var gameModel = ModelInit.getGameModelValid();

        var rankResponse = RepositoryInit.getRankValid(gameModel.getLogin(), true);
        var movieResponse = RepositoryInit.getMovies();
        gameModel.setTitle(movieResponse.get(0).getTitle());

//        ReflectionTestUtils.setField(gameService, "MOVIES", movieResponse);
//        ReflectionTestUtils.setField(gameService, "MOVIES_ACTUAL_ROUND", movieResponse);

        when(rankRepository.findByLogin(gameModel.getLogin())).thenReturn(rankResponse);
        when(rankRepository.save(Mockito.any(Rank.class))).thenReturn(rankResponse.get());

        when(movieRepository.findAll()).thenReturn(movieResponse);

        var rank = gameService.nextRound(gameModel);

        assertEquals(rank.getMessage(), "Congratulations! You are finished the game");
        assertEquals(rank.getMovies(), null);
    }

    @Test
    public void nextRoundWithSuccessTitleWrong() {

        var gameModel = ModelInit.getGameModelValid();

        var rankResponse = RepositoryInit.getRankValid(gameModel.getLogin(), true);
        var movieResponse = RepositoryInit.getMovies();

        ReflectionTestUtils.setField(gameService, "MOVIES", movieResponse);
        ReflectionTestUtils.setField(gameService, "MOVIES_ACTUAL_ROUND", movieResponse);

        when(rankRepository.findByLogin(gameModel.getLogin())).thenReturn(rankResponse);
        when(rankRepository.save(Mockito.any(Rank.class))).thenReturn(rankResponse.get());

        when(movieRepository.findAll()).thenReturn(movieResponse);

        var rank = gameService.nextRound(gameModel);

        assertEquals(rank.getMessage(), "Take care, you have more 2 chances");
        assertEquals(rank.getMovies().size(), 2);
    }

    @Test
    public void nextRoundWithSuccessTitleNull() {

        var gameModel = ModelInit.getGameModelValid();
        gameModel.setTitle(null);

        var rankResponse = RepositoryInit.getRankValid(gameModel.getLogin(), true);
        var movieResponse = RepositoryInit.getMovies();

        ReflectionTestUtils.setField(gameService, "MOVIES", movieResponse);
        ReflectionTestUtils.setField(gameService, "MOVIES_ACTUAL_ROUND", movieResponse);

        when(rankRepository.findByLogin(gameModel.getLogin())).thenReturn(rankResponse);
        when(rankRepository.save(Mockito.any(Rank.class))).thenReturn(rankResponse.get());

        when(movieRepository.findAll()).thenReturn(movieResponse);

        var rank = gameService.nextRound(gameModel);

        assertEquals(rank.getMessage(), "You have to choose a movie");
        assertEquals(rank.getMovies().size(), 2);
    }

    @Test
    public void nextRoundWithSuccessMore3Errors() {

        var gameModel = ModelInit.getGameModelValid();

        var rankResponse = RepositoryInit.getRankValid(gameModel.getLogin(), true);
        var movieResponse = RepositoryInit.getMovies();

        ReflectionTestUtils.setField(gameService, "MOVIES", movieResponse);
        ReflectionTestUtils.setField(gameService, "MOVIES_ACTUAL_ROUND", movieResponse);
        ReflectionTestUtils.setField(gameService, "ERRORS", 3);

        when(rankRepository.findByLogin(gameModel.getLogin())).thenReturn(rankResponse);
        when(rankRepository.save(Mockito.any(Rank.class))).thenReturn(rankResponse.get());
        when(movieRepository.findAll()).thenReturn(movieResponse);


        Throwable exception = assertThrows(BusinessException.class, () -> gameService.nextRound(gameModel));

        assertEquals("You reached the maximum number of error allowed", exception.getMessage());
    }

    @Test
    public void nextRoundErrorInGame() {

        var gameModel = ModelInit.getGameModelValid();

        var rankResponse = RepositoryInit.getRankValid(gameModel.getLogin(), false);
        var movieResponse = RepositoryInit.getMovies();

        ReflectionTestUtils.setField(gameService, "MOVIES", movieResponse);

        when(rankRepository.findByLogin(gameModel.getLogin())).thenReturn(rankResponse);
        when(rankRepository.save(Mockito.any(Rank.class))).thenReturn(rankResponse.get());

        when(movieRepository.findAll()).thenReturn(movieResponse);

        Throwable exception = assertThrows(BusinessException.class, () -> gameService.nextRound(gameModel));

        assertEquals("Player is not in game, please start the game", exception.getMessage());
    }
}
