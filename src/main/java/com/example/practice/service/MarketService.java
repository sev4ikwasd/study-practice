package com.example.practice.service;

import com.example.practice.dto.HeadingRequest;
import com.example.practice.dto.MarketRequest;
import com.example.practice.dto.MarketResponse;
import com.example.practice.model.Market;

import java.util.List;
import java.util.UUID;

public interface MarketService {
    List<MarketResponse> getAllMarkets();
    MarketResponse getMarketById(UUID id);
    Market createMarket(MarketRequest request);
    void updateMarket(UUID marketId, MarketRequest request);
    void blockMarket(UUID marketId, boolean toBlock);
    MarketResponse addHeading(UUID marketId, HeadingRequest request);
    void removeHeading(UUID marketId, UUID headingId);
    void removeMarket(UUID id);
}
