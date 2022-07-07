package com.example.practice.model;

import lombok.*;

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
