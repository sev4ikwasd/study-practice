package com.example.practice.exception;

import java.util.UUID;

public class MarketNotFoundException extends RuntimeException {
    public MarketNotFoundException(UUID id) {
        super("Market with id " + id.toString() + " was not found");
    }
}
