package com.example.practice.model;

import lombok.Builder;
import lombok.Data;
import lombok.Singular;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class Market {
    @Builder.Default
    private UUID id = UUID.randomUUID();
    private String name;
    private String category;
    @Singular
    private List<Heading> headings;
    private boolean isBlocked;

    public Market(UUID id, String name, String category, List<Heading> headings, boolean isBlocked) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.headings = new ArrayList<>(headings);
        this.isBlocked = isBlocked;
    }
}
