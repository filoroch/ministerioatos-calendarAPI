package br.org.ministerioatos.calendarAPI.Evento.DTOs;

import java.time.LocalDate;
import java.util.Optional;

public record EventoRequestDTO(
        String titulo,
        Optional<String> descricao,
        LocalDate dataInicio,
        Optional<LocalDate> dataFim
) {
}
