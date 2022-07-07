package com.example.practice.dto;

import com.example.practice.model.Heading;

import java.util.List;
import java.util.UUID;

public record MarketResponse(UUID id,
                             String name,
                             String category,
                             int headingCount,
                             List<Heading> headings,
                             boolean isBlocked) {
}

