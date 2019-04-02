package com.aa.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.aa.exception.SignUpException;

@ControllerAdvice
@Slf4j
public class ExceptionHandlers extends BaseExceptionHandler {
    public ExceptionHandlers() {
        super(log);
        registerMapping(SignUpException.class, "USER_1123123", "sdfafdasf", HttpStatus.CREATED);
    }
}
