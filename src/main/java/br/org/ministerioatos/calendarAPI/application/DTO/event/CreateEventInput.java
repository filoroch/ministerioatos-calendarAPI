package br.org.ministerioatos.calendarAPI.application.DTO.event;

import br.org.ministerioatos.calendarAPI.application.DTO.SubEventDTO;
import br.org.ministerioatos.calendarAPI.application.DTO.adress.AdressInput;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;
import java.util.List;

public record CreateEventInput(
    @Schema(description = "Título do evento", example = "Culto de Familia")
    @NotBlank
    String title,

    @Schema(description = "Descrição detalhada do evento", example = "Culto principal que reune todo o ministerio. Todos os domingos as 19h")
    String description,

    @Schema(description = "Data e hora de início do evento", example = "2025-11-30T19:00:00")
    LocalDateTime startDateTime,

    @Schema(description = "Data e hora de fim do evento (se não informado, será dataInicio + 30 minutos)", example = "2025-11-30T21:00:00")
    LocalDateTime endDateTime,

    @Schema(
        description = "Lista de sub eventos associados ao evento",
        type = "array",
        implementation = SubEventDTO.class,
        example = "[{\"title\": \"Abertura\", \"date\": \"2025-11-30T19:00:00\"}, {\"title\": \"Louvor\", \"date\": \"2025-11-30T19:30:00\"}]"
    )
    @Valid
    List<SubEventDTO> subEvents,

    @Schema(description = "Objeto que representa o endereço do evento", implementation = AdressInput.class, nullable = true)
    @Valid
    AdressInput adress
) {
}
