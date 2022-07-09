package com.example.practice.dto;

import java.util.UUID;

public record MarketStatsResponse(UUID id, int headingCount, double averagePriceCents) {
}
