package com.tirabassi.javamoviesbattle.domain.mappers;

import com.tirabassi.javamoviesbattle.domain.models.MovieModel;
import com.tirabassi.javamoviesbattle.domain.models.obdm.ImdbResponseModel;
import com.tirabassi.javamoviesbattle.domain.models.obdm.SearchModel;
import org.springframework.stereotype.Component;

@Component
public class MovieMapper {

    public static MovieModel toMovieComplete(SearchModel search, ImdbResponseModel imdb){

        var movieComplete = new MovieModel();
        movieComplete.setTitle(search.getTitle());
        movieComplete.setImdbID(search.getImdbID());
        movieComplete.setType(search.getType());
        movieComplete.setYear(search.getYear());
        movieComplete.setPoster(search.getPoster());
        movieComplete.setImdbRating(imdb.getImdbRating());

        return movieComplete;
    }
}
