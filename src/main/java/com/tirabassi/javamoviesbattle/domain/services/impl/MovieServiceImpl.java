package com.tirabassi.javamoviesbattle.domain.services.impl;

import com.tirabassi.javamoviesbattle.domain.mappers.MovieMapper;
import com.tirabassi.javamoviesbattle.domain.models.MovieModel;
import com.tirabassi.javamoviesbattle.domain.services.MovieService;
import com.tirabassi.javamoviesbattle.domain.webservices.OmdbWebservice;
import com.tirabassi.javamoviesbattle.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    OmdbWebservice omdbWebservice;

    @Override
    public List<MovieModel> searchMoviesWithCompleteInfo(String type, String name, Integer page) throws IOException, InterruptedException {

        var moviesModel = new ArrayList<MovieModel>();
        var movies = omdbWebservice.searchMovies(type, name, page);

        movies.getSearch().forEach(movie -> {
            try {
                var im = omdbWebservice.findMovieByIMDB(movie.getImdbID());

                var model = MovieMapper.toMovieComplete(movie, im);
                moviesModel.add(model);

            } catch (IOException | InterruptedException e) {
                throw new BusinessException(e.getMessage());
            }
        });

        return  moviesModel;
    }
}
