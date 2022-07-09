package com.example.practice.controller;

import com.example.practice.dto.InfoResponse;
import com.example.practice.dto.StatsResponse;
import com.example.practice.service.InfoService;
import com.example.practice.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class InfoController {
    private final InfoService infoService;
    private final StatsService statsService;

    @GetMapping("/info")
    private ResponseEntity<InfoResponse> getInfo() {
        return ResponseEntity.ok(infoService.getInfo());
    }

    @GetMapping("/stats")
    private ResponseEntity<StatsResponse> getStats() {
        return ResponseEntity.ok(statsService.getStats());
    }
}
