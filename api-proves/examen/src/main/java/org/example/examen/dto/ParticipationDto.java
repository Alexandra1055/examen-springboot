package org.example.examen.dto;

import org.example.examen.domain.EventCategory;
import org.example.examen.domain.Sex;

public record ParticipationDto(
        Long id,
        Long athleteId,
        String athleteName,
        String athleteCountry,
        Integer athleteAge,
        Sex athleteSex,
        Long eventId,
        String eventName,
        EventCategory eventCategory,
        Sex eventSex,
        boolean eventFinished,
        String result,
        Integer position
) {}
