package com.example.practice.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.IOException;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "csv-file")
@Getter
@Setter
@Slf4j
public class CsvFileConfig {
    private String path;

    @Bean(name="csvFile")
    public File csvFile() throws IOException {
        File file = new File(getPath());
        if (file.createNewFile())
            log.info("Creating new file at: " + getPath());
        else
            log.info("Using existing csv file at: " + getPath());
        return file;
    }
}
