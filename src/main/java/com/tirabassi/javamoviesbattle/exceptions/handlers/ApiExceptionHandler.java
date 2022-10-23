package com.tirabassi.javamoviesbattle.exceptions.handlers;

import com.tirabassi.javamoviesbattle.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public Error handlerNegocioException(BusinessException ex, WebRequest webRequest){

        HttpStatus httpStatus = HttpStatus.PRECONDITION_FAILED;

        Error error = Error.builder()
                        .date(OffsetDateTime.now())
                        .status(httpStatus.value())
                        .message(ex.getMessage())
                        .build();

        return error;
    }
}
