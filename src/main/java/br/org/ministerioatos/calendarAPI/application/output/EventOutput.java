package br.org.ministerioatos.calendarAPI.application.output;

import br.org.ministerioatos.calendarAPI.application.DTO.SubEventDTO;
import java.util.List;
import java.util.Map;

public record EventOutput(
    Integer id,
    String title,
    String description,
    Map<String, String> date,
    List<SubEventDTO> subEvents,
    AdressOutput location
) {
}
