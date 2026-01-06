package br.org.ministerioatos.calendarAPI.presentation.controller;

import br.org.ministerioatos.calendarAPI.application.input.CreateEventInput;
import br.org.ministerioatos.calendarAPI.application.usecase.CreateEventUseCase;
import br.org.ministerioatos.calendarAPI.application.input.FindEventInput;
import br.org.ministerioatos.calendarAPI.application.usecase.FindEventUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/evento")
@Tag(name = "Evento", description = "Gerenciamento de eventos para visualização no frontend do calendario")
public class EventoController {

    private final CreateEventUseCase createEvent;
    private final FindEventUseCase findEvent;

    public EventoController(CreateEventUseCase createEvent, FindEventUseCase findEvent) {
        this.createEvent = createEvent;
        this.findEvent = findEvent;
    }

    //TODO: Adicionar paginação
    @GetMapping()
    @Operation(summary = "Obter todos os eventos", description = "Recupera uma lista de todos os eventos cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos recuperada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity getEvents(
            @ParameterObject FindEventInput filter,
            @PageableDefault(size = 30, page = 0, sort = "dataHoraInicio", direction = Sort.Direction.ASC) Pageable pageable
    ){
        /// Preciso retornar uma pagina com todos os eventos
        var output = findEvent.execute(filter, pageable);
        return ResponseEntity.ok(output);
    }
//
    @PostMapping()
    @Operation(summary = "Criar novo evento", description = "Cria um novo evento com ou sem subeventos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito ao criar evento (ex: evento já existe)"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity createEvent(
            @RequestBody @Valid CreateEventInput input
    ){
        var output = createEvent.execute(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(output);
    }
    //TODO: Get event by id
    //TODO: Update event by id
    //TODO: Delete event by id
}
