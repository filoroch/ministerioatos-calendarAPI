package br.org.ministerioatos.calendarAPI.module.Evento.DTOs.response;

import br.org.ministerioatos.calendarAPI.module.Local.model.Local;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Schema(description = "Dados de resposta de um evento")
public record EventoResponseDTO(
        @Schema(description = "Título do evento", example = "Culto de Familia")
        String titulo,

        @Schema(description = "Descrição do evento")
        String descricao,

        @Schema(description = "Data e hora de início", example = "2025-11-30T19:00:00")
        LocalDateTime dataHoraInicio,

        @Schema(description = "Data e hora de fim", example = "2025-11-30T21:00:00")
        LocalDateTime dataHoraFim,

        @Schema(description = "Lista de subeventos")
        List<SubEventoResponseDTO> subEventos,

        @Schema(description = "Local do evento", example = "{'rua':'Rua das Flores','numero':123,'complemento':'Sala 101','cidade':'São Paulo','CEP':'01310-100','UF':'SP'}")
        Local local
) {
}
