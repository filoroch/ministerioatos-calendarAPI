package br.org.ministerioatos.calendarAPI.Evento.DTOs;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EventoResponseDTO(
        String titulo,
        String descricao,
        LocalDate datainicio,
        LocalDate dataFim
) {
}
