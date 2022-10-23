package com.tirabassi.javamoviesbattle.domain.models;

import lombok.*;


@Data
public class UserModel {

    private String login;

    private String password;

    private Boolean admin = false;
}
