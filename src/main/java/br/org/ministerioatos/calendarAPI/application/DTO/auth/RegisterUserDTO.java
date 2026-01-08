package br.org.ministerioatos.calendarAPI.application.DTO.auth;

import io.swagger.v3.oas.annotations.media.Schema;

public record RegisterUserDTO(

    @Schema(description = "User's login name", example = "user123")
    String login,
    String password
) {
}
