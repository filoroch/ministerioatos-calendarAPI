package br.org.ministerioatos.calendarAPI.application.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;

public record AdressInput(

    @Schema(name = "Rua", description = "Nome da rua", example = "Avenida Paulista")
    String street,

    @Schema(name = "Número", description = "Número do endereço", example = "123")
    Integer number,

    @Schema(name = "Bairro", description = "Nome do bairro", example = "Centro")
    String neighborhood,

    @Schema(name = "Cidade", description = "Nome da cidade", example = "São Paulo")
    String city,

    @Schema(name = "Estado", description = "Estado com duas letras maiusculas", example = "SP")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Estado inválido")
    String state,

    @Schema(name = "CEP", description = "Código de Endereçamento Postal no formato 00000-000", example = "12345-678")
    @Pattern(regexp = "^(\\d{5}-\\d{3})$", message = "CEP inválido")
    String zipCode
) {
}
