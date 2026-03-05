package org.example.sports.dto;

import org.example.sports.domain.EventCategory;
import org.example.sports.domain.Sex;

public record EventDto(
        Long id,
        String name,
        EventCategory category,
        Sex sex,
        boolean finished
) {}
