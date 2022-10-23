package com.tirabassi.javamoviesbattle.domain.mappers;

import com.tirabassi.javamoviesbattle.domain.entities.User;
import com.tirabassi.javamoviesbattle.domain.models.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public static UserModel toModel(User user){

        var model = new UserModel();
        model.setLogin(user.getLogin());
        model.setPassword(user.getPassword());
        model.setAdmin(user.getAdmin());

        return model;
    }
}
