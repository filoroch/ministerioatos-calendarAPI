package br.org.ministerioatos.calendarAPI.application.output;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

public record AdressOutput(
    String street,
    Integer number,
    String neighborhood,
    String city,
    String state,
    String zipCode
){
}
