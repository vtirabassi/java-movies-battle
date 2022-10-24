package com.tirabassi.javamoviesbattle.domain.services;

import com.github.javafaker.Faker;
import com.tirabassi.javamoviesbattle.Init.ModelInit;
import com.tirabassi.javamoviesbattle.Init.RepositoryInit;
import com.tirabassi.javamoviesbattle.domain.entities.User;
import com.tirabassi.javamoviesbattle.domain.repositories.UserRepository;
import com.tirabassi.javamoviesbattle.domain.services.impl.UserServiceImpl;
import com.tirabassi.javamoviesbattle.securities.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    JwtService jwtService;

    private Faker faker;

    @BeforeEach
    public void init(){
        faker = new Faker();
        initMocks(this);
    }

    @Test
    public void loadUserByUsername() {

        var name = faker.name().firstName();

        var userResponse = RepositoryInit.getUserValid(name);
        userResponse.get().setPassword(faker.name().lastName());
        userResponse.get().setAdmin(true);

        when(userRepository.findByLogin(name)).thenReturn(userResponse);

        var user = userService.loadUserByUsername(name);

        assertNotEquals(user, null);
    }

    @Test
    public void loadUserByUsernameNotAdm() {

        var name = faker.name().firstName();

        var userResponse = RepositoryInit.getUserValid(name);
        userResponse.get().setPassword(faker.name().lastName());
        userResponse.get().setAdmin(false);

        when(userRepository.findByLogin(name)).thenReturn(userResponse);

        var user = userService.loadUserByUsername(name);

        assertNotEquals(user, null);
    }

    @Test
    public void createSuccess() {

        var userModel = ModelInit.getUserValid();
        var user = RepositoryInit.getUserValid(userModel.getLogin());

        when(passwordEncoder.encode(userModel.getLogin())).thenReturn(faker.lorem().toString());
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user.get());

        var userResponse = userService.create(userModel);

        assertNotEquals(userResponse, null);
    }

    @Test
    public void authenticateSuccess(){

        var credentialModel = ModelInit.getCredentialValid();

        var userResponse = RepositoryInit.getUserValid(credentialModel.getLogin());
        userResponse.get().setPassword(credentialModel.getPassword());

        var jwt = faker.lorem().toString();

        when(userRepository.findByLogin(credentialModel.getLogin())).thenReturn(userResponse);
        when(passwordEncoder.matches(credentialModel.getPassword(), userResponse.get().getPassword())).thenReturn(true);
        when(jwtService.createToken(Mockito.any(User.class))).thenReturn(jwt);

        var token = userService.authenticate(credentialModel);

        assertEquals(token.login, credentialModel.getLogin());
        assertNotEquals(token.token, null);
    }

    @Test
    public void authenticateInvalidPassword(){

        var credentialModel = ModelInit.getCredentialValid();

        var userResponse = RepositoryInit.getUserValid(credentialModel.getLogin());
        userResponse.get().setPassword(credentialModel.getPassword());

        var jwt = faker.lorem().toString();

        when(userRepository.findByLogin(credentialModel.getLogin())).thenReturn(userResponse);
        when(passwordEncoder.matches(credentialModel.getPassword(), userResponse.get().getPassword())).thenReturn(false);
        when(jwtService.createToken(Mockito.any(User.class))).thenReturn(jwt);


        Throwable exception = assertThrows(ResponseStatusException.class, () -> userService.authenticate(credentialModel));

        assertThat(exception.getMessage(), containsString("Invalid password"));
    }


}
