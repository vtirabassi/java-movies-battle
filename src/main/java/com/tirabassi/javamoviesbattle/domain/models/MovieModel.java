package com.tirabassi.javamoviesbattle.domain.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MovieModel {

    private String title;

    private String year;

    private String imdbID;

    private String type;

    private String poster;

    private String imdbRating;
}
