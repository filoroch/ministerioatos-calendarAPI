package br.org.ministerioatos.calendarAPI.module.Evento.controller;

import br.org.ministerioatos.calendarAPI.module.Evento.DTOs.request.EventoRequestDTO;
import br.org.ministerioatos.calendarAPI.module.Evento.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/evento")
@Tag(name = "Evento", description = "Gerenciamento de eventos para visualização no frontend do calendario")
public class EventoController {

    @Autowired
    EventoService eventoService;

    //TODO: Adicionar paginação
    @GetMapping()
    @Operation(summary = "Obter todos os eventos", description = "Recupera uma lista de todos os eventos cadastrados no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de eventos recuperada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity getEvents(
            @RequestParam(name = "title", required = false) String title,
            @RequestParam(name = "startDateTimeRange", required = false) String startDateTimeRange,
            @RequestParam(name = "endDateTimeRange", required = false) String endDateTimeRange
    ){
        if (title != null && !title.isEmpty() || startDateTimeRange != null || endDateTimeRange != null) {
            var filteredData = eventoService.findByFilters(
                    title,
                    startDateTimeRange != null ? LocalDateTime.parse(startDateTimeRange) : null,
                    endDateTimeRange != null ? LocalDateTime.parse(endDateTimeRange) : null
            );
            return ResponseEntity.ok(filteredData);
        }

        var data = eventoService.findAllEvents();
        return ResponseEntity.ok(data);
    }

    @PostMapping()
    @Operation(summary = "Criar novo evento", description = "Cria um novo evento com ou sem subeventos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "409", description = "Conflito ao criar evento (ex: evento já existe)"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity createEvent(@RequestBody EventoRequestDTO eventoRequestDTO){
        var newEvent = eventoService.createEvent(eventoRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newEvent);
    }
    //TODO: Get event by id
    //TODO: Update event by id
    //TODO: Delete event by id
}
