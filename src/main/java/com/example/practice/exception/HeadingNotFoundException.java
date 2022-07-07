package com.example.practice.exception;

import java.util.UUID;

public class HeadingNotFoundException extends RuntimeException {
    public HeadingNotFoundException(UUID id) {
        super("Heading with id " + id.toString() + " was not found");
    }
}
