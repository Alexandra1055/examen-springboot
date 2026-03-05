package org.example.examen.dto;

import org.example.examen.domain.EventCategory;
import org.example.examen.domain.Sex;

public record EventDto(
        Long id,
        String name,
        EventCategory category,
        Sex sex,
        boolean finished
) {}
