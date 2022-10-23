package com.tirabassi.javamoviesbattle.domain.services;

import com.tirabassi.javamoviesbattle.domain.models.MovieModel;

import java.io.IOException;
import java.util.List;

public interface MovieService {

    List<MovieModel> searchMoviesWithCompleteInfo(String type, String name, Integer page) throws IOException, InterruptedException;
}
