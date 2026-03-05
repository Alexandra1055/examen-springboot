package org.example.examen.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.examen.dto.AthleteDto;
import org.example.examen.dto.CreateAthleteDto;
import org.example.examen.mapper.AthleteMapper;
import org.example.examen.service.AthleteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/athletes")
@RequiredArgsConstructor
public class AthleteController {

    private final AthleteService athleteService;
    private final AthleteMapper athleteMapper;

    @GetMapping
    public List<AthleteDto> findAll() {
        return athleteService.findAll().stream()
                .map(athleteMapper::toDto)
                .toList();
    }

    @PostMapping
    public ResponseEntity<AthleteDto> create(@Valid @RequestBody CreateAthleteDto request) {
        var created = athleteService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(athleteMapper.toDto(created));
    }

    @PutMapping("/{id}")
    public AthleteDto update(@PathVariable Long id, @Valid @RequestBody CreateAthleteDto request) {
        var updated = athleteService.update(id, request);
        return athleteMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        athleteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
