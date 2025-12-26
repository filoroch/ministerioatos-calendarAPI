package br.org.ministerioatos.calendarAPI.module.Evento.DTOs.request;

import br.org.ministerioatos.calendarAPI.module.Local.DTO.LocalRequestDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Builder
@Schema(description = "Dados para criar um novo evento")
public record EventoRequestDTO(
        @Schema(description = "Título do evento", example = "Culto de Familia")
        String titulo,

        @Schema(description = "Descrição detalhada do evento", example = "Culto principal que reune todo o ministerio. Todos os domingos as 19h")
        Optional<String> descricao,

        @Schema(description = "Data e hora de início do evento", example = "2025-11-30T19:00:00")
        Optional<LocalDateTime> dataHoraInicio, ///  passar now() como default na controller se n for informado

        @Schema(description = "Data e hora de fim do evento (se não informado, será dataInicio + 30 minutos)", example = "2025-11-30T21:00:00")
        Optional<LocalDateTime> dataFim,

        @Schema(description = "Lista de subeventos associados ao evento")
        List<SubEventoRequestDTO> subEventos,

        @Schema(description = "ID de um local já cadastrado", example = "1")
        Optional<Integer> idLocalExistente,

        @Schema(description = "Objeto que representa o local do evento", implementation = LocalRequestDTO.class)
        Optional<LocalRequestDTO> local
) {
}
