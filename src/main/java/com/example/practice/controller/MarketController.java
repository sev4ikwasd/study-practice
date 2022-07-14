package com.example.practice.controller;

import com.example.practice.dto.HeadingRequest;
import com.example.practice.dto.MarketRequest;
import com.example.practice.dto.MarketResponse;
import com.example.practice.service.MarketService;
import com.example.practice.validation.ValidUUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Validated
public class MarketController {
    private final MarketService marketService;

    @GetMapping("/market")
    public ResponseEntity<List<MarketResponse>> getAllMarkets() {
        return ResponseEntity.ok(marketService.getAllMarkets());
    }

    @GetMapping("/market/{marketId}")
    public ResponseEntity<MarketResponse> getMarket(@PathVariable @ValidUUID String marketId) {
        return ResponseEntity.ok(marketService.getMarketById(UUID.fromString(marketId)));
    }

    @PostMapping("/market")
    public ResponseEntity<MarketResponse> createMarket(@Valid @RequestBody MarketRequest request) {
        MarketResponse response = marketService.createMarket(request);
        return ResponseEntity.created(URI.create("market/" + response.id())).body(response);
    }

    @PutMapping("/market/{marketId}")
    public ResponseEntity<MarketResponse> changeMarket(@PathVariable @ValidUUID String marketId,
                                             @Valid @RequestBody MarketRequest request) {
        return ResponseEntity.ok(marketService.updateMarket(UUID.fromString(marketId), request));
    }

    @GetMapping("/market/{marketId}/block")
    public ResponseEntity<MarketResponse> blockMarket(@PathVariable @ValidUUID String marketId) {
        return ResponseEntity.ok(marketService.blockMarket(UUID.fromString(marketId), true));
    }

    @GetMapping("/market/{marketId}/unblock")
    public ResponseEntity<MarketResponse> unblockMarket(@PathVariable @ValidUUID String marketId) {
        return ResponseEntity.ok(marketService.blockMarket(UUID.fromString(marketId), false));
    }

    @PostMapping("/market/{marketId}/addHeading")
    public ResponseEntity<MarketResponse> addMarketHeading(@PathVariable @ValidUUID String marketId,
                                                           @Valid @RequestBody HeadingRequest request) {
        return ResponseEntity.ok(marketService.addHeading(UUID.fromString(marketId), request));
    }

    @DeleteMapping("/market/{marketId}/removeHeading/{headingId}")
    public ResponseEntity<MarketResponse> removeMarketHeading(@PathVariable("marketId") @ValidUUID String marketId,
                                                    @PathVariable("headingId") @ValidUUID String headingId) {
        return ResponseEntity.ok(marketService.removeHeading(UUID.fromString(marketId), UUID.fromString(headingId)));
    }

    @DeleteMapping("/market/{marketId}")
    public ResponseEntity<Void> deleteMarket(@PathVariable @ValidUUID String marketId) {
        marketService.removeMarket(UUID.fromString(marketId));
        return ResponseEntity.ok().build();
    }
}
