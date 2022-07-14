package com.example.practice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class HeadingNotFoundException extends RuntimeException {
    public HeadingNotFoundException(UUID id) {
        super("Heading with id " + id.toString() + " was not found");
    }
}
