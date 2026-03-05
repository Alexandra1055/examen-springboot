package org.example.examen.service;

import lombok.RequiredArgsConstructor;
import org.example.examen.domain.Event;
import org.example.examen.dto.EventRequest;
import org.example.examen.error.ResourceNotFoundException;
import org.example.examen.mapper.EventMapper;
import org.example.examen.repository.EventRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
    }

    public Event create(EventRequest request) {
        Event event = eventMapper.toEntity(request);
        return eventRepository.save(event);
    }

    public Event update(Long id, EventRequest request) {
        Event event = findById(id);
        eventMapper.updateEntity(event, request);
        return eventRepository.save(event);
    }

    public void delete(Long id) {
        Event event = findById(id);
        eventRepository.delete(event);
    }
}
