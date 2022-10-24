package com.tirabassi.javamoviesbattle.domain.webservices;

import com.tirabassi.javamoviesbattle.domain.models.obdm.ImdbResponseModel;
import com.tirabassi.javamoviesbattle.domain.models.obdm.OmdbResponseModel;

import java.io.IOException;

public interface OmdbWebservice {

    OmdbResponseModel searchMovies(String type, String name, Integer page) throws IOException, InterruptedException;
    ImdbResponseModel findMovieByIMDB(String imdb) throws IOException, InterruptedException;
}
