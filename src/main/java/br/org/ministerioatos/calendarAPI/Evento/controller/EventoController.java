package br.org.ministerioatos.calendarAPI.Evento.controller;

import br.org.ministerioatos.calendarAPI.Evento.DTOs.EventoResponseDTO;
import br.org.ministerioatos.calendarAPI.Evento.service.EventoService;
import br.org.ministerioatos.calendarAPI.infrastructure.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/evento")
@Tag(name = "Evento", description = "Gerenciamento de eventos. Permite criar, editar, visualizar e excluir eventos")
public class EventoController {

    @Autowired
    EventoService eventoService;

    //TODO: Get all events (ainda não paginavel)
    @GetMapping()
    @Operation(
        summary = "Retorna todos os eventos (não paginado)",
        description = "Retorna uma lista de todos os eventos registrados de forma estruturada"
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lista de eventos")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "204", description = "Lista de eventos vazia")
    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Gateway")
    public ResponseEntity<ApiResponse<List<EventoResponseDTO>>> getAllEvents(){
        var data = eventoService.findAllEvents();

        if (data.isEmpty()){
            var responseEmpty = new ApiResponse<List<EventoResponseDTO>>(false, HttpStatus.NO_CONTENT, "Lista de eventos vazia",null);
            return ResponseEntity.ok(responseEmpty);
        }
        var response = new ApiResponse<List<EventoResponseDTO>>(true, HttpStatus.ACCEPTED, "Lista de eventos", data);
        return ResponseEntity.ok(response);
    }
    //TODO: Create event
    //TODO: Get event by id
    //TODO: Update event by id
    //TODO: Delete event by id
}
