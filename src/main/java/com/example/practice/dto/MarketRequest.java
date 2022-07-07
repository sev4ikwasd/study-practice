package com.example.practice.dto;

import javax.validation.constraints.NotBlank;

public record MarketRequest(@NotBlank String name,
                            @NotBlank String category) {
}
