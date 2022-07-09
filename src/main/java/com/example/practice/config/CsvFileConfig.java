package com.example.practice.config;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "csv-file")
@Getter
@Setter
@Slf4j
public class CsvFileConfig {
    private String path;

    @Bean(name = "csvFile")
    public Path csvFile() {
        Path file = Paths.get(getPath());
        try {
            Files.createFile(file);
            log.info("Creating new file at: " + getPath());
        } catch (FileAlreadyExistsException e) {
            log.info("Using existing csv file at: " + getPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
}
