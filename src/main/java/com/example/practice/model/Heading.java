package com.example.practice.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Heading {
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String name;
    private String description;
    private int priceCents;
}
