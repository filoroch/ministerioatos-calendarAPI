package br.org.ministerioatos.calendarAPI.Evento.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder()
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NonNull
    @Setter
    private  String titulo;
    @Setter
    private String descricao;
    @Setter
    private LocalDate dataInicio;
    @Setter
    private LocalDate dataFim;

    public Evento(@NonNull String titulo, LocalDate dataInicio) {
        this.titulo = titulo;
        this.dataInicio = dataInicio;
    }


}

