package org.example.sports.dto;

import org.example.sports.domain.Sex;

public record AthleteDto(
        Long id,
        String name,
        String country,
        Integer age,
        Sex sex
) {}
