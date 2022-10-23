package com.tirabassi.javamoviesbattle.exceptions.handlers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class Error {

    private OffsetDateTime date;
    private Integer status;
    private String message;
    private List<FieldError> fildErrors;
}
