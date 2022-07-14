package com.example.practice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class MarketNotFoundException extends RuntimeException {
    public MarketNotFoundException(UUID id) {
        super("Market with id " + id.toString() + " was not found");
    }
}
