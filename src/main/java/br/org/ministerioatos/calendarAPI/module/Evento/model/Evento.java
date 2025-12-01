package br.org.ministerioatos.calendarAPI.module.Evento.model;

import br.org.ministerioatos.calendarAPI.module.Local.model.Local;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder()
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @Setter
    private  String titulo;

    @Setter
    private String descricao;

    @Setter
    private LocalDateTime dataHoraInicio;

    @Setter
    private LocalDateTime dataHoraFim;

    @OneToMany (mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    @JsonManagedReference
    private List<SubEvento> subEventos = new ArrayList<>();

    @ManyToOne
    @JoinColumn (name = "id_local")
    private Local local;

}

