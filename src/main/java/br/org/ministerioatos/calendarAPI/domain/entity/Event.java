package br.org.ministerioatos.calendarAPI.domain.entity;

import br.org.ministerioatos.calendarAPI.domain.objectValue.Adress;
import br.org.ministerioatos.calendarAPI.domain.objectValue.SubEvent;

import java.time.LocalDateTime;
import java.util.List;

public class Event {
    private String title;
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private List<SubEvent> subEvents;
    private Adress adress;

    public Event(
            String title,
            String description,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            List<SubEvent> subEvents,
            Adress adress
    ) {
        this.title = title;
        this.description = description;

        setStartDateTime(startDateTime);
        setEndDateTime(endDateTime);
        this.subEvents = subEvents;
        this.adress = adress;
    }

    /// Data de inicio precisa obrigatoriamente conter valor ou sera igual a data/hora atual
    private void setStartDateTime(LocalDateTime startDateTime) {
        if (startDateTime == null || startDateTime.isBefore(LocalDateTime.now())) {
            this.startDateTime = LocalDateTime.now();
        } else {
            this.startDateTime = startDateTime;
        }
    }

    private void setEndDateTime(LocalDateTime endDateTime) {
        if (endDateTime == null || endDateTime.isBefore(this.startDateTime)) {
            this.endDateTime = this.startDateTime.plusMinutes(30);
        } else {
            this.endDateTime = endDateTime;
        }
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public List<SubEvent> getSubEvents() {
        return subEvents;
    }

    public Adress getAdress() {
        return adress;
    }

    public void resolveConflictsData() {
        this.startDateTime.plusMinutes(30);
        this.endDateTime = this.startDateTime.plusMinutes(30);
    }
}
