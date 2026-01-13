package br.org.ministerioatos.calendarAPI.application.DTO.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record RegisterUserDTO(

    @Schema(description = "User's login name", example = "user123")
    @NotBlank
    String login,
    @Schema(description = "User's password", example = "P@ssw0rd!")
    @NotBlank
    String password
) {
}
