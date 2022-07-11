package com.example.practice.dto;

public record InfoResponse(String name,
                           String description,
                           String creator,
                           String variant,
                           int year) {
}
