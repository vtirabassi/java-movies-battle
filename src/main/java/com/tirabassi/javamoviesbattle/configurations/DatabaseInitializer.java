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
        return new CommandLineRunner() {

            @Override
            public void run(String... args) throws Exception {

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

//                var movieDomain = new Movie();
//                movieDomain.setTitle("Teste");
//                movieDomain.setYear("2022");
//                movieDomain.setPoster("img");
//                movieDomain.setRate((7.5));
//
//                movieRepository.save(movieDomain);
//
//                var movieDomain2 = new Movie();
//                movieDomain.setTitle("Teste2");
//                movieDomain.setYear("2022");
//                movieDomain.setPoster("img");
//                movieDomain.setRate((8.0));
//
//                movieRepository.save(movieDomain2);
//
//                var movieDomain3 = new Movie();
//                movieDomain.setTitle("Teste3");
//                movieDomain.setYear("2022");
//                movieDomain.setPoster("img");
//                movieDomain.setRate((5.0));
//
//                movieRepository.save(movieDomain3);
//                var movieDomain4 = new Movie();
//                movieDomain.setTitle("Teste4");
//                movieDomain.setYear("2022");
//                movieDomain.setPoster("img");
//                movieDomain.setRate((10.0));
//
//                movieRepository.save(movieDomain4);
            }
        };
    }
}
