package br.org.ministerioatos.calendarAPI.infrastructure.data.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "subevents")
public class SubEventDataJpa {
    @ManyToOne
    @JoinColumn(name = "id_evento")
    @JsonBackReference
    private EventDataJpa event;

    @Id
    @GeneratedValue
    private Integer id_sub_evento;
    private String title;
    private LocalDateTime date;

    public SubEventDataJpa(String title, LocalDateTime date) {
        this.title = title;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
