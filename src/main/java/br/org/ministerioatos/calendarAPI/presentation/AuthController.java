package br.org.ministerioatos.calendarAPI.presentation;

import br.org.ministerioatos.calendarAPI.application.DTO.auth.AuthInput;
import br.org.ministerioatos.calendarAPI.application.DTO.auth.RegisterUserDTO;
import br.org.ministerioatos.calendarAPI.application.service.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Operações relacionadas à autenticação de usuários")
public class AuthController {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private UserDetailsServiceImpl service;

    @PostMapping("/register")
    @Operation(summary = "Registar usuarios na aplicação", description = "Registra usuarios da api por meio de login e senha. ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuario criado"),
            @ApiResponse(responseCode = "403", description = "Acesso não autorizado")
    })
    public ResponseEntity register(@RequestBody @Valid RegisterUserDTO dto) throws URISyntaxException {
        var output = service.register(dto);
        return ResponseEntity.created(new URI("/users/" + output.getUsername())).body(output);
    }

    @PostMapping("/signin")
    public ResponseEntity signIn(@RequestBody @Valid AuthInput dto) {
        var token = new UsernamePasswordAuthenticationToken(dto.login(), dto.password());
        var authenticated = authManager.authenticate(token);
        return ResponseEntity.ok().build();
    }
}
