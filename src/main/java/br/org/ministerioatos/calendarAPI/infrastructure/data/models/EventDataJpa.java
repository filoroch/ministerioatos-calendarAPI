package br.org.ministerioatos.calendarAPI.infrastructure.data.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
public class EventDataJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, name = "titulo")
    private String title;

    @Column(nullable = false, name = "descricao")
    private String description;

    @Column(nullable = false, name = "data_inicio")
    private LocalDateTime startDateTime;

    @Column(nullable = false, name = "data_fim")
    private LocalDateTime endDateTime;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<SubEventDataJpa> subEvents = new ArrayList<>();

    @Embedded
    private AdressDataJpa adress;

    public EventDataJpa(
            String title,
            String description,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            List<SubEventDataJpa> subEvents,
            AdressDataJpa adress
    ) {
        this.title = title;
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.subEvents = subEvents;
        this.adress = adress;
    }

    public Integer getId() {
        return id;
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

    public List<SubEventDataJpa> getSubEvents() {
        return subEvents;
    }

    public AdressDataJpa getAdress() {
        return adress;
    }
}
