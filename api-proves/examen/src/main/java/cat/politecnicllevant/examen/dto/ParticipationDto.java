package cat.politecnicllevant.examen.dto;

import cat.politecnicllevant.examen.domain.EventCategory;
import cat.politecnicllevant.examen.domain.Sex;

public class ParticipationDto {
    private Long id;
    private Long athleteId;
    private String athleteName;
    private String athleteCountry;
    private Integer athleteAge;
    private Sex athleteSex;
    private Long eventId;
    private String eventName;
    private EventCategory eventCategory;
    private Sex eventSex;
    private boolean eventFinished;
    private String result;
    private Integer position;
}
