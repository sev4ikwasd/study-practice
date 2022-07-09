package com.example.practice.service.impl;

import com.example.practice.dto.MarketResponse;
import com.example.practice.dto.MarketStatsResponse;
import com.example.practice.dto.StatsResponse;
import com.example.practice.model.Heading;
import com.example.practice.service.MarketService;
import com.example.practice.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {
    private final MarketService marketService;

    @Override
    public StatsResponse getStats() {
        List<MarketResponse> markets = marketService.getAllMarkets();
        int overallHeadingCount = markets.stream().map(MarketResponse::headingCount).reduce(0, Integer::sum);
        List<MarketStatsResponse> marketStatsResponses = markets.stream()
                .map(marketResponse -> {
                    double averagePrice = marketResponse.headings().stream()
                            .map(Heading::getPriceCents).mapToInt(i -> i).average().orElse(0);
                    return new MarketStatsResponse(marketResponse.id(), marketResponse.headingCount(), averagePrice);
                }).toList();
        return new StatsResponse(markets.size(), overallHeadingCount, marketStatsResponses);
    }
}
