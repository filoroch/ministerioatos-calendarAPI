package br.org.ministerioatos.calendarAPI.application.DTO;

import java.time.LocalDateTime;

public record SubEventDTO(
    String title,
    LocalDateTime date
) {
}
