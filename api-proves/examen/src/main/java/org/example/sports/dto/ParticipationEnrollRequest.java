package org.example.sports.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ParticipationEnrollRequest(
        @NotNull(message = "athleteId is required")
        @Positive(message = "athleteId must be > 0")
        Long athleteId,

        @NotNull(message = "eventId is required")
        @Positive(message = "eventId must be > 0")
        Long eventId
) {}
