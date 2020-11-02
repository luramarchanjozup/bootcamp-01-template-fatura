package com.marcoscoutozup.fatura.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {

    private final MessageSource messageSource;

    private final Logger log = LoggerFactory.getLogger(ControllerAdvice.class);

    public ControllerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public StandardException handlerMethodArgumentNotValidException(MethodArgumentNotValidException e){

        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> messageSource.getMessage(error, Locale.getDefault()))
                .collect(Collectors.toList());

        log.warn("Tratamento do erro MethodArgumentNotValidException: {}", errors);

        return new StandardException(HttpStatus.BAD_REQUEST.value(), errors);
    }
}
