package br.org.ministerioatos.calendarAPI.Evento.controller;

import br.org.ministerioatos.calendarAPI.ApiResponse;
import br.org.ministerioatos.calendarAPI.Evento.DTOs.EventoResponseDTO;
import br.org.ministerioatos.calendarAPI.Evento.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/evento")
public class EventoController {

    @Autowired
    EventoService eventoService;

    //TODO: Get all events (ainda não paginavel)
    @GetMapping()
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
