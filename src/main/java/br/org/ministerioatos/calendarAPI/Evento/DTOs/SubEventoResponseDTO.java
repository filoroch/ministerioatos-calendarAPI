package br.org.ministerioatos.calendarAPI.Evento.DTOs;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Dados de resposta de um subevento")
public record SubEventoResponseDTO(
        @Schema(description = "Título do subevento", example = "Culto Infantil")
        String titulo,

        @Schema(description = "Data e hora de início", example = "2025-11-30T19:30:00")
        LocalDateTime dataInicio
) {
}

