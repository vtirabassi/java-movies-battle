package com.tirabassi.javamoviesbattle.securities;

import com.tirabassi.javamoviesbattle.domain.services.impl.UserServiceImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {

    private JwtService jwtService;
    private UserServiceImpl userService;

    public JwtAuthFilter(JwtService jwtService, UserServiceImpl userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        var authorization = httpServletRequest.getHeader("Authorization");

        if(authorization != null && authorization.startsWith("Bearer")){
            var token = authorization.split(" ")[1];
            var valid = jwtService.tokenIsValid(token);

            if(valid){
                var userLogin = jwtService.getLoginUser(token);
                var userDetails = userService.loadUserByUsername(userLogin);

                var user =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                //preciso fazer para o contexto do spring security entenda que é uma autenticacao web
                user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(user);

            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}

