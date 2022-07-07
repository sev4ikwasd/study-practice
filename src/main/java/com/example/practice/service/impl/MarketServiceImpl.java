package com.example.practice.service.impl;

import com.example.practice.dto.HeadingRequest;
import com.example.practice.dto.MarketRequest;
import com.example.practice.dto.MarketResponse;
import com.example.practice.exception.HeadingNotFoundException;
import com.example.practice.model.Heading;
import com.example.practice.model.Market;
import com.example.practice.repository.MarketRepository;
import com.example.practice.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MarketServiceImpl implements MarketService {
    private final MarketRepository marketRepository;

    private MarketResponse marketToMarketResponse(Market market) {
        return new MarketResponse(
                market.getId(),
                market.getName(),
                market.getCategory(),
                market.getHeadings().size(),
                market.getHeadings(),
                market.isBlocked());
    }

    private List<MarketResponse> marketListToMarketResponseList(List<Market> markets) {
        return markets.stream().map(this::marketToMarketResponse).toList();
    }

    @Override
    public List<MarketResponse> getAllMarkets() {
        return marketListToMarketResponseList(marketRepository.getAllMarkets());
    }

    @Override
    public MarketResponse getMarketById(UUID id) {
        return marketToMarketResponse(marketRepository.getMarketById(id));
    }

    @Override
    public Market createMarket(MarketRequest request) {
        return marketRepository.saveMarket(
                Market.builder()
                        .name(request.name())
                        .category(request.category())
                        .isBlocked(false).build());
    }

    @Override
    public void updateMarket(UUID marketId, MarketRequest request) {
        marketRepository.saveMarket(
                Market.builder()
                        .id(marketId)
                        .name(request.name())
                        .category(request.category())
                        .isBlocked(false).build());
    }

    @Override
    public void blockMarket(UUID marketId, boolean toBlock) {
        Market market = marketRepository.getMarketById(marketId);
        market.setBlocked(toBlock);
        marketRepository.saveMarket(market);
    }

    @Override
    public MarketResponse addHeading(UUID marketId, HeadingRequest request) {
        Market market = marketRepository.getMarketById(marketId);
        Heading heading = Heading.builder()
                .name(request.name())
                .description(request.description())
                .priceCents(request.priceCents()).build();
        market.getHeadings().add(heading);
        return marketToMarketResponse(marketRepository.saveMarket(market));
    }

    @Override
    public void removeHeading(UUID marketId, UUID headingId) {
        Market market = marketRepository.getMarketById(marketId);
        Heading headingToRemove = market.getHeadings().stream()
                .filter(heading -> heading.getId().equals(headingId)).findFirst()
                .orElseThrow(() -> new HeadingNotFoundException(headingId));
        market.getHeadings().remove(headingToRemove);
        marketRepository.saveMarket(market);
    }

    @Override
    public void removeMarket(UUID id) {
        marketRepository.removeMarket(id);
    }
}
