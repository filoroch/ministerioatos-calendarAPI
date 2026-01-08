package br.org.ministerioatos.calendarAPI.application.DTO.auth;

import io.swagger.v3.oas.annotations.media.Schema;

public record AuthInput(
    @Schema(description = "User's login name", example = "user123")
    String login,
    @Schema(description = "User's password", example = "P@ssw0rd!")
    String password
) {
}
