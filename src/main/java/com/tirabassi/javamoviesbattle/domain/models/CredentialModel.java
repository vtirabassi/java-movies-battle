package com.tirabassi.javamoviesbattle.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialModel {

    @NotEmpty(message = "{usuario.login.obrigatorio}")
    public String login;

    @NotEmpty(message = "{usuario.senha.obrigatorio}")
    public String password;
}