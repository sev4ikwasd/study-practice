package com.example.practice.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record HeadingRequest(@NotBlank String name,
                             @NotBlank String description,
                             @NotNull @Min(0) Integer priceCents) {
}
