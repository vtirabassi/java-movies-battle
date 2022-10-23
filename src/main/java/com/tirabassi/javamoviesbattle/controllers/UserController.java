package com.tirabassi.javamoviesbattle.controllers;

import com.tirabassi.javamoviesbattle.domain.mappers.UserMapper;
import com.tirabassi.javamoviesbattle.domain.models.CredentialModel;
import com.tirabassi.javamoviesbattle.domain.models.TokenModel;
import com.tirabassi.javamoviesbattle.domain.entities.User;
import com.tirabassi.javamoviesbattle.domain.models.UserModel;
import com.tirabassi.javamoviesbattle.domain.repositories.UserRepository;
import com.tirabassi.javamoviesbattle.domain.services.impl.UserServiceImpl;
import com.tirabassi.javamoviesbattle.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@RequestBody @Valid UserModel usuario){

        return userService.create(usuario);
    }

    @PostMapping("auth")
    @ResponseStatus(HttpStatus.OK)
    public TokenModel auth(@RequestBody @Valid CredentialModel credential){
        return userService.authenticate(credential);
    }

    @GetMapping("{login}")
    @ResponseStatus(HttpStatus.OK)
    public UserModel findByLogin(@PathVariable String login){
        var user = userRepository.findByLogin(login)
                .orElseThrow(() -> new BusinessException("Not Found Login"));

        return UserMapper.toModel(user);
    }
}
