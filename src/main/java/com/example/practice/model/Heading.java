package com.example.practice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Heading {
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String name;
    private String description;
    private int priceCents;
}
