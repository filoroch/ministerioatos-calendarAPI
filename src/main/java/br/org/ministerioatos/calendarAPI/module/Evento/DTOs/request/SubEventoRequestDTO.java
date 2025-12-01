package br.org.ministerioatos.calendarAPI.module.Evento.DTOs.request;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Dados para criar um subevento")
public record SubEventoRequestDTO(
        @Schema(description = "Título do subevento", example = "Culto Infantil - No auditorio")
        String titulo,

        @Schema(description = "Data e hora de início do subevento", example = "2025-11-30T19:30:00")
        LocalDateTime dataInicio
) {
}

