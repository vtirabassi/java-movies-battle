package com.tirabassi.javamoviesbattle.domain.services.impl;

import com.tirabassi.javamoviesbattle.domain.entities.User;
import com.tirabassi.javamoviesbattle.domain.models.CredentialModel;
import com.tirabassi.javamoviesbattle.domain.models.TokenModel;
import com.tirabassi.javamoviesbattle.domain.models.UserModel;
import com.tirabassi.javamoviesbattle.domain.repositories.UserRepository;
import com.tirabassi.javamoviesbattle.exceptions.InvalidPasswordException;
import com.tirabassi.javamoviesbattle.securities.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        User user = userRepository.findByLogin(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String[] roles = user.getAdmin().equals(true)
                ? new String[]{"USER", "ADMIN"}
                : new String[]{"USER"};

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getLogin())
                .password(user.getPassword())
                .roles(roles)
                .build();
    }

    @Transactional
    public User create(UserModel user) {

        var userDomain = new User();
        userDomain.setLogin(user.getLogin());
        userDomain.setPassword(passwordEncoder.encode(user.getPassword()));
        userDomain.setAdmin(user.getAdmin());

        return userRepository.save(userDomain);
    }

    public TokenModel authenticate(CredentialModel credential) {
        try {
            UserDetails userDetails = loadUserByUsername(credential.getLogin());
            boolean matches = passwordEncoder.matches(credential.getPassword(), userDetails.getPassword());

            var user = new User();
            user.setLogin(credential.getLogin());
            user.setPassword(credential.getPassword());

            if(matches){
                String token = jwtService.createToken(user);

                return TokenModel.builder()
                        .login(credential.getLogin())
                        .token(token).build();
            }

            throw new InvalidPasswordException("Invalid password");

        }catch (UsernameNotFoundException | InvalidPasswordException ex){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, ex.getMessage());
        }
    }
}
