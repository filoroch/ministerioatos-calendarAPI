package br.org.ministerioatos.calendarAPI.application.input;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record FindEventInput(

        @Schema(description = "Title of the event to search for", example = "Annual Meeting")
        String title,

        @Schema(description = "Start of the date-time range for the event search", example = "2024-01-01T00:00:00")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime startDateTimeRange,

        @Schema(description = "End of the date-time range for the event search", example = "2024-12-31T23:59:59")
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime endDateTimeRange
) {
}
