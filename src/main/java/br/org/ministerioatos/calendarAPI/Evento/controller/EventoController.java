package br.org.ministerioatos.calendarAPI.Evento.controller;

import br.org.ministerioatos.calendarAPI.Evento.DTOs.EventoRequestDTO;
import br.org.ministerioatos.calendarAPI.Evento.DTOs.EventoResponseDTO;
import br.org.ministerioatos.calendarAPI.Evento.service.EventoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/evento")
@Tag(name = "Evento", description = "Gerenciamento de eventos para visualização no frontend do calendario")
public class EventoController {

    @Autowired
    EventoService eventoService;

    //TODO: Get all events (ainda não paginavel)
//    @GetMapping()
//    public ResponseEntity getAllEvents(){
//        var data = eventoService.findAllEvents();
//
//        if (data.isEmpty()){
//            var responseEmpty = new ApiResponse<List<EventoResponseDTO>>(false, HttpStatus.NO_CONTENT, "Lista de eventos vazia",null);
//            return ResponseEntity.ok(responseEmpty);
//        }
//        var response = new ApiResponse<List<EventoResponseDTO>>(true, HttpStatus.ACCEPTED, "Lista de eventos", data);
//        return ResponseEntity.ok(response);
//    }
    //TODO: Create event
    @PostMapping()
    @Operation(summary = "Criar novo evento", description = "Cria um novo evento com ou sem subeventos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Evento criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
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
