package br.org.ministerioatos.calendarAPI.module.Evento.controller;

import br.org.ministerioatos.calendarAPI.exceptions.evento.EventAlredyExists;
import br.org.ministerioatos.calendarAPI.infrastructure.GlobalExceptionHandler;
import br.org.ministerioatos.calendarAPI.module.Evento.DTOs.request.EventoRequestDTO;
import br.org.ministerioatos.calendarAPI.module.Evento.DTOs.response.EventoResponseDTO;
import br.org.ministerioatos.calendarAPI.module.Evento.service.EventoService;
import br.org.ministerioatos.calendarAPI.module.Local.DTO.LocalRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static br.org.ministerioatos.calendarAPI.module.Local.service.LocalService.toLocalModel;
import static java.time.LocalDateTime.now;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(EventoController.class)
@Import(GlobalExceptionHandler.class)
class EventoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockitoBean
    EventoService service;

    @Nested
    @DisplayName("Get Events")
    class getAllEventsEndpoint{

        List<EventoResponseDTO> events;

        @BeforeEach
        void setup() {
            events = List.of(
                    EventoResponseDTO.builder()
                            .titulo("Evento 1")
                            .descricao("Descrição do Evento 1")
                            .dataHoraInicio(now())
                            .dataHoraFim(now().plusHours(1))
                            .subEventos(List.of())
                            .build(),
                    EventoResponseDTO.builder()
                            .titulo("Evento 2")
                            .descricao("Descrição do Evento 2")
                            .dataHoraInicio(now().plusDays(1))
                            .dataHoraFim(now().plusDays(1).plusHours(2))
                            .subEventos(List.of())
                            .build()
            );
        }

        /// Verificar o endpoint /api/evento com seus query parameters
        /// Verificar filtro por titulo
        @Test
        @DisplayName("200 - Deve retornar eventos filtrados por título")
        void shouldReturnEventsFilteredByTitle() throws Exception {

            when(service.findByTitle("Reunião de Teste"))
                    .thenReturn(events);

            mockMvc.perform(get("/api/evento")
                    .param("titulo", "Reunião de Teste"))
                    .andExpect(status().isOk());

            verify(service, times(1)).findByTitle("Reunião de Teste");

        }
        /// Verificar filtro por data

    }


    @Nested
    @DisplayName("Create Event Endpoint")
    class createEventEndpoint{

        private LocalRequestDTO local;
        private EventoRequestDTO validRequest;

        @BeforeEach
        void setup() {
            local = LocalRequestDTO.builder()
                    .rua("Rua das Flores")
                    .numero(123)
                    .complemento("Apto 45")
                    .bairro("Jardim Primavera")
                    .cidade("São Paulo")
                    .CEP("01234-567")
                    .UF("SP")
                    .build();

            validRequest = EventoRequestDTO.builder()
                    .titulo("Reunião de Teste")
                    .descricao(Optional.of("Descrição do evento de teste"))
                    .dataHoraInicio(Optional.of(now()))
                    .dataFim(Optional.empty())
                    .subEventos(List.of())
                    .idLocalExistente(Optional.empty())
                    .local(Optional.of(local))
                    .build();
        }

        ///  Esse teste verifica se o endpoint de criação de evento funciona corretamente quando dados válidos são fornecidos.

        @Test
        @DisplayName("201 - Deve criar um evento com sucesso quando dados válidos forem fornecidos")
        void shouldCreateEventSuccessfullyWhenValidDataProvided() throws Exception {

            var response = EventoResponseDTO.builder()
                    .titulo("Reunião de Teste")
                    .descricao("Descrição do evento de teste")
                    .dataHoraInicio(validRequest.dataHoraInicio().get())
                    .dataHoraFim(validRequest.dataHoraInicio().get().plusMinutes(30))
                    .local(toLocalModel(local))
                    .subEventos(List.of())
                    .build();

            when(service.createEvent(validRequest)).thenReturn(response);

            mockMvc.perform(post("/api/evento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(validRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.titulo").value(response.titulo()))
                    .andExpect(jsonPath("$.descricao").value(response.descricao()))
                    .andExpect(jsonPath("$.local.rua").value(response.local().getRua()))
                    .andExpect(jsonPath("$.local.numero").value(response.local().getNumero()))
                    .andExpect(jsonPath("$.local.bairro").value(response.local().getBairro()))
                    .andExpect(jsonPath("$.local.cidade").value(response.local().getCidade()))
                    .andExpect(jsonPath("$.local.cep").value(response.local().getCEP()))
                    .andExpect(jsonPath("$.local.uf").value(response.local().getUF()));

            verify(service, times(1)).createEvent(validRequest);
        }

        @Test
        @DisplayName("409 - Deve retornar erro quando tentar criar um evento que já existe")
        void shouldReturnErrorWhenTryingToCreateAnEventThatAlreadyExists() throws Exception {

            when(service.createEvent(any(EventoRequestDTO.class)))
                    .thenThrow(new EventAlredyExists());

            mockMvc.perform(post("/api/evento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(validRequest)))
                    .andExpect(status().isConflict())
                    .andExpect(content().contentType(MediaType.APPLICATION_PROBLEM_JSON))
                    .andExpect(jsonPath("$.title").value("EventAlredyExists"));

            verify(service, times(1)).createEvent(any(EventoRequestDTO.class));
        }
    }
}