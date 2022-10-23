package com.tirabassi.javamoviesbattle.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenModel {
    public String login;
    public String token;
}
