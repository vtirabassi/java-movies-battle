package com.tirabassi.javamoviesbattle.domain.services;

import com.github.javafaker.Faker;
import com.tirabassi.javamoviesbattle.Init.WebserviceInit;
import com.tirabassi.javamoviesbattle.domain.services.impl.MovieServiceImpl;
import com.tirabassi.javamoviesbattle.domain.webservices.OmdbWebservice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class MovieServiceImplTest {

    @InjectMocks
    MovieServiceImpl movieService;

    @Mock
    OmdbWebservice omdbWebservice;

    private Faker faker;

    @BeforeEach
    public void init(){
        faker = new Faker();

        MockitoAnnotations.initMocks(this);

    }

    @Test
    public void searchMoviesWithCompleteInfoWithSuccess()
            throws IOException, InterruptedException {

        var type = faker.lorem().fixedString(5);
        var name = faker.lorem().fixedString(5);
        var page = faker.number().randomDigit();

        var obdmResponse = WebserviceInit.getValidOmdb();
        var imdbResponse = WebserviceInit.getValidImdb();

        when(omdbWebservice.searchMovies(type, name, page)).thenReturn(obdmResponse);
        when(omdbWebservice.findMovieByIMDB(obdmResponse.getSearch().get(0).getImdbID())).thenReturn(imdbResponse);

        var movies = movieService.searchMoviesWithCompleteInfo(type, name, page);

        assertEquals(movies.get(0).getImdbRating(), imdbResponse.getImdbRating());
    }
}
