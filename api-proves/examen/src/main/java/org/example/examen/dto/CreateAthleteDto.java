package org.example.examen.dto;

import jakarta.validation.constraints.*;
import org.example.examen.domain.Sex;

public record CreateAthleteDto(
        @NotBlank(message = "name is required")
        String name,

        @NotBlank(message = "country is required")
        String country,

        @NotNull(message = "age is required")
        @Min(value = 1, message = "age must be >= 1")
        Integer age,

        @NotNull(message = "sex is required")
        Sex sex
) {}
