package com.tirabassi.javamoviesbattle.exceptions.handlers;

import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.OffsetDateTime;

public class ForbiddenExceptionHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse res, AuthenticationException e) throws IOException, ServletException {

        var json = new JSONObject();
        json.put("date", OffsetDateTime.now().toString());
        json.put("status", String.valueOf(403));
        json.put("message", "Access denied");


        res.setContentType("application/json;charset=UTF-8");
        res.setStatus(403);
        res.getWriter().write(json.toString());
    }
}
