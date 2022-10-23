package com.tirabassi.javamoviesbattle.configurations;

import com.tirabassi.javamoviesbattle.domain.entities.Movie;
import com.tirabassi.javamoviesbattle.domain.repositories.MovieRepository;
import com.tirabassi.javamoviesbattle.domain.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieService movieService;

    @Bean
    CommandLineRunner loadDatabase() {
        return args -> {

            var movies = movieService.searchMoviesWithCompleteInfo("movie", "marvel", 1);

            movies.forEach(movie ->
                    {
                        var movieDomain = new Movie();
                        movieDomain.setTitle(movie.getTitle());
                        movieDomain.setYear(movie.getYear());
                        movieDomain.setPoster(movieDomain.getPoster());
                        movieDomain.setRating((movie.getImdbRating()));

                        movieRepository.save(movieDomain);
                    });
        };
    }
}
