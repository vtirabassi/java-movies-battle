package com.tirabassi.javamoviesbattle.Init;

import com.github.javafaker.Faker;
import com.tirabassi.javamoviesbattle.domain.models.CredentialModel;
import com.tirabassi.javamoviesbattle.domain.models.GameModel;
import com.tirabassi.javamoviesbattle.domain.models.UserModel;

public class ModelInit {

    private static Faker faker = new Faker();

    public static GameModel getGameModelValid(){
        var game = new GameModel();
        game.setLogin(faker.name().firstName());
        game.setTitle(faker.name().lastName());

        return game;
    }

    public static UserModel getUserValid(){
        var user = new UserModel();

        user.setLogin(faker.name().firstName());
        user.setPassword(faker.name().lastName());
        user.setAdmin(true);

        return user;
    }

    public static CredentialModel getCredentialValid(){

        var user = getUserValid();

        var credential = new CredentialModel();
        credential.setLogin(user.getLogin());
        credential.setPassword(user.getPassword());

        return credential;
    }
}
