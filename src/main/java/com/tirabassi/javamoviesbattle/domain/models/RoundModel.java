package com.tirabassi.javamoviesbattle.domain.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class RoundModel {

    public RoundModel(String message, @Nullable List<MovieModel> movies) {
        this.message = message;
        this.movies = movies;
    }

    private String message;

    @Nullable
    private List<MovieModel> movies;
}
