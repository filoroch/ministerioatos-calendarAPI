package br.org.ministerioatos.calendarAPI.domain.objectValue;

import java.time.LocalDateTime;

public class SubEvent {
    private String title;
    private LocalDateTime data;

    public SubEvent(String title, LocalDateTime data) {
        this.title = title;
        this.data = data;
    }
    public SubEvent(String title){
        this.title = title;
        this.data = LocalDateTime.now();
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getData() {
        return data;
    }
}
