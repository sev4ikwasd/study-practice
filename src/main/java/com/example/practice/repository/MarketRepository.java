package com.example.practice.repository;

import com.example.practice.exception.MarketNotFoundException;
import com.example.practice.model.Market;

import java.util.List;
import java.util.UUID;

public interface MarketRepository {
    List<Market> getAllMarkets();
    Market getMarketById(UUID id) throws MarketNotFoundException;
    Market saveMarket(Market market);
    void removeMarket(UUID id) throws MarketNotFoundException;
}
