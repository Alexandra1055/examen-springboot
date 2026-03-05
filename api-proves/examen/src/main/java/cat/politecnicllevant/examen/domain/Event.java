package cat.politecnicllevant.examen.domain;

import cat.politecnicllevant.examen.domain.EventCategory;
import cat.politecnicllevant.examen.domain.Sex;

public class Event {
    private Long id;
    private String name;
    private EventCategory category;
    private Sex sex;
    private boolean finished;
}
