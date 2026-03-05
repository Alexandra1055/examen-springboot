package org.example.sports.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sports.dto.ParticipationDto;
import org.example.sports.dto.ParticipationEnrollRequest;
import org.example.sports.mapper.ParticipationMapper;
import org.example.sports.service.ParticipationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participations")
@RequiredArgsConstructor
public class ParticipationController {

    private final ParticipationService participationService;
    private final ParticipationMapper participationMapper;

    // Endpoint protegit (JWT)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/event/{eventId}")
    public List<ParticipationDto> findByEvent(@PathVariable Long eventId) {
        return participationService.findByEvent(eventId).stream()
                .map(participationMapper::toDto)
                .toList();
    }

    // Endpoint protegit (JWT)
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/enroll")
    public ResponseEntity<ParticipationDto> enroll(@Valid @RequestBody ParticipationEnrollRequest request) {
        var created = participationService.enrollWithoutResult(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(participationMapper.toDto(created));
    }
}
