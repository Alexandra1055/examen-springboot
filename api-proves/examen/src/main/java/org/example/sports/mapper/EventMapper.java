package org.example.sports.mapper;

import org.example.sports.domain.Event;
import org.example.sports.dto.EventDto;
import org.example.sports.dto.EventRequest;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

    public EventDto toDto(Event e) {
        if (e == null) return null;
        return new EventDto(e.getId(), e.getName(), e.getCategory(), e.getSex(), e.isFinished());
    }

    public Event toEntity(EventRequest req) {
        if (req == null) return null;
        return Event.builder()
                .name(req.name())
                .category(req.category())
                .sex(req.sex())
                .finished(req.finished())
                .build();
    }

    public void updateEntity(Event e, EventRequest req) {
        e.setName(req.name());
        e.setCategory(req.category());
        e.setSex(req.sex());
        e.setFinished(req.finished());
    }
}
