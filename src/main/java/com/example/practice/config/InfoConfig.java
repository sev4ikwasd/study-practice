package com.example.practice.config;

import com.example.practice.dto.InfoResponse;
import com.example.practice.service.InfoService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "info")
@Getter
@Setter
public class InfoConfig {
    private String name;
    private String description;
    private String creator;
    private String variant;
    private int year;

    @Bean
    public InfoService infoService() {
        return () -> new InfoResponse(name, description, creator, variant, year);
    }
}