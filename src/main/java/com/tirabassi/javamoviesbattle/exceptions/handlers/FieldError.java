package com.tirabassi.javamoviesbattle.exceptions.handlers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldError {

    private String field;
    private String message;
}