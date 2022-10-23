package com.tirabassi.javamoviesbattle.domain.models;

import io.swagger.models.auth.In;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.Optional;

@Data
public class GameModel {

    private String login;

    @Nullable
    private String title;
}
