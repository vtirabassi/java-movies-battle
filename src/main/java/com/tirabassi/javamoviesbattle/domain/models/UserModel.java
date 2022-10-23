package com.tirabassi.javamoviesbattle.domain.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserModel {

    private String login;

    private String password;

    private Boolean admin = false;
}
