package com.example.practice.dto;

import java.util.List;

public record StatsResponse(int marketCount, int overallHeadingCount, List<MarketStatsResponse> marketStats) {
}
