package br.org.ministerioatos.calendarAPI.Evento.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
public class SubEvento {

    @ManyToOne
    @JoinColumn (name = "id_evento")
    @Setter
    @JsonBackReference
    private Evento evento;

    @Id
    @GeneratedValue
    private Integer id_sub_evento;
    @Setter
    private String titulo;
    @Setter
    private LocalDateTime dataInicio;
}
