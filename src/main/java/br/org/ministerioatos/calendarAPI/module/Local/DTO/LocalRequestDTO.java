package br.org.ministerioatos.calendarAPI.module.Local.DTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "Dados para cadastrar um novo local")
public record LocalRequestDTO(
        @Schema(description = "Nome da rua", example = "Rua das Flores")
        String rua,

        @Schema(description = "Número do endereço", example = "123")
        Integer numero,

        @Schema(description = "Complemento do endereço", example = "Sala 101")
        String complemento,

        @Schema(description = "Bairro", example = "Jardim Paulista")
        String bairro,

        @Schema(description = "Cidade", example = "São Paulo")
        String cidade,

        @Schema(description = "CEP", example = "01310-100")
        String CEP,

        @Schema(description = "Estado (UF)", example = "SP")
        String UF
) {
}

