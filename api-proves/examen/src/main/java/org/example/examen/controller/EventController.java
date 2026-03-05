package org.example.examen.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.examen.dto.EventDto;
import org.example.examen.dto.EventRequest;
import org.example.examen.mapper.EventMapper;
import org.example.examen.service.EventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public List<EventDto> findAll() {
        return eventService.findAll().stream()
                .map(eventMapper::toDto)
                .toList();
    }

    @PostMapping
    public ResponseEntity<EventDto> create(@Valid @RequestBody EventRequest request) {
        var created = eventService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventMapper.toDto(created));
    }

    @PutMapping("/{id}")
    public EventDto update(@PathVariable Long id, @Valid @RequestBody EventRequest request) {
        var updated = eventService.update(id, request);
        return eventMapper.toDto(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
