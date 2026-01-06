package br.org.ministerioatos.calendarAPI.application.input;

import br.org.ministerioatos.calendarAPI.application.DTO.SubEventDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record CreateEventInput(
    @Schema(name = "Titulo", description = "Título do evento", example = "Culto de Familia")
    @NotBlank
    String title,

    @Schema(name = "Descricao", description = "Descrição detalhada do evento", example = "Culto principal que reune todo o ministerio. Todos os domingos as 19h")
    String description,

    @Schema(name = "Data e hora de inicio do evento", description = "Data e hora de início do evento", example = "2025-11-30T19:00:00")
    LocalDateTime startDateTime,

    @Schema(name = "Data e hora de fim do evento", description = "Data e hora de fim do evento (se não informado, será dataInicio + 30 minutos)", example = "2025-11-30T21:00:00")
    LocalDateTime endDateTime,

    @Schema(name = "Sub eventos", description = "Lista de sub eventos associados ao evento", implementation = SubEventDTO.class)
    @Valid
    List<SubEventDTO> subEvents,

    @Schema(name = "Endereço do evento", description = "Objeto que representa o endereço do evento", implementation = AdressInput.class, nullable = true)
    @Valid
    AdressInput adress
) {
}
