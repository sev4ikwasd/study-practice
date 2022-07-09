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
    public ResponseEntity<Void> createMarket(@Valid @RequestBody MarketRequest request) {
        return ResponseEntity.created(URI.create("market/" + marketService.createMarket(request).getId())).build();
    }

    @PutMapping("/market/{marketId}")
    public ResponseEntity<Void> changeMarket(@PathVariable @ValidUUID String marketId,
                                             @Valid @RequestBody MarketRequest request) {
        marketService.updateMarket(UUID.fromString(marketId), request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/market/{marketId}/block")
    public ResponseEntity<Void> blockMarket(@PathVariable @ValidUUID String marketId) {
        marketService.blockMarket(UUID.fromString(marketId), true);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/market/{marketId}/unblock")
    public ResponseEntity<Void> unblockMarket(@PathVariable @ValidUUID String marketId) {
        marketService.blockMarket(UUID.fromString(marketId), false);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/market/{marketId}/addHeading")
    public ResponseEntity<MarketResponse> addMarketHeading(@PathVariable @ValidUUID String marketId,
                                                           @Valid @RequestBody HeadingRequest request) {
        return ResponseEntity.ok(marketService.addHeading(UUID.fromString(marketId), request));
    }

    @DeleteMapping("/market/{marketId}/removeHeading/{headingId}")
    public ResponseEntity<Void> removeMarketHeading(@PathVariable("marketId") @ValidUUID String marketId,
                                                    @PathVariable("headingId") @ValidUUID String headingId) {
        marketService.removeHeading(UUID.fromString(marketId), UUID.fromString(headingId));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/market/{marketId}")
    public ResponseEntity<Void> deleteMarket(@PathVariable @ValidUUID String marketId) {
        marketService.removeMarket(UUID.fromString(marketId));
        return ResponseEntity.ok().build();
    }
}
