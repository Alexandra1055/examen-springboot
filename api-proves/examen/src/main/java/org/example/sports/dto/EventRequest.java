package org.example.sports.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.example.sports.domain.EventCategory;
import org.example.sports.domain.Sex;

public record EventRequest(
        @NotBlank(message = "name is required")
        String name,

        @NotNull(message = "category is required")
        EventCategory category,

        @NotNull(message = "sex is required")
        Sex sex,

        boolean finished
) {}
