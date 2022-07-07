package com.example.practice.repository.impl;

import com.example.practice.exception.MarketNotFoundException;
import com.example.practice.model.Heading;
import com.example.practice.model.Market;
import com.example.practice.repository.MarketRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class SampleMarketRepository implements MarketRepository {
    List<Market> markets = new ArrayList<>(Arrays.asList(
            Market.builder().name("Test1").category("Food").isBlocked(false)
                    .heading(Heading.builder().name("Bread").description("A loaf of bread").priceCents(299).build())
                    .heading(Heading.builder().name("Meat").description("A quarter pound of tenderloin").priceCents(999).build()).build(),
            Market.builder().name("Test2").category("Alcohol").isBlocked(false)
                    .heading(Heading.builder().name("Brandy").description("A fine brandy").priceCents(1599).build())
                    .heading(Heading.builder().name("Cognac").description("A georgian premium cognac").priceCents(1499).build())
                    .heading(Heading.builder().name("Beer").description("Belgian beer").priceCents(199).build()).build()));

    @Override
    public List<Market> getAllMarkets() {
        return new ArrayList<>(markets);
    }

    @Override
    public Market getMarketById(UUID id) {
        return markets.stream().filter(market -> market.getId().equals(id)).findFirst()
                .orElseThrow(() -> new MarketNotFoundException(id));
    }

    @Override
    public Market saveMarket(Market market) {
        markets.stream().filter(filterMarket -> filterMarket.getId().equals(market.getId())).findFirst().ifPresent(value -> markets.remove(value));
        markets.add(market);
        return market;
    }

    @Override
    public void removeMarket(UUID id) {
        Market marketToDelete = markets.stream().filter(market -> market.getId().equals(id)).findFirst()
                .orElseThrow(() -> new MarketNotFoundException(id));
        markets.remove(marketToDelete);
    }
}
