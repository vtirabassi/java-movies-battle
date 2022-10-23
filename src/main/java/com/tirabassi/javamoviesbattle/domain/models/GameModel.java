package com.tirabassi.javamoviesbattle.domain.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.lang.Nullable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class GameModel {

    private String login;

    @Nullable
    private String title;
}
