package br.org.ministerioatos.calendarAPI.module.Evento.controller;

import br.org.ministerioatos.calendarAPI.module.Evento.DTOs.request.EventoRequestDTO;
import br.org.ministerioatos.calendarAPI.module.Evento.DTOs.response.EventoResponseDTO;
import br.org.ministerioatos.calendarAPI.module.Evento.service.EventoService;
import br.org.ministerioatos.calendarAPI.module.Local.DTO.LocalRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static br.org.ministerioatos.calendarAPI.module.Local.service.LocalService.toLocalModel;
import static java.time.LocalDateTime.now;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(EventoController.class)
class EventoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockitoBean
    EventoService service;

    @Nested
    @DisplayName("Create Event Endpoint")
    class createEventEndpoint{

        @Test
        @DisplayName("201 - Deve criar um evento com sucesso quando dados válidos forem fornecidos")
        void shouldCreateEventSuccessfullyWhenValidDataProvided() throws Exception {

            ///  Define um local de teste
            var local = LocalRequestDTO.builder()
                    .rua("Rua das Flores")
                    .numero(123)
                    .complemento("Apto 45")
                    .bairro("Jardim Primavera")
                    .cidade("São Paulo")
                    .CEP("01234-567")
                    .UF("SP")
                    .build();

            /// Define um evento de teste
            var request = EventoRequestDTO.builder()
                    .titulo("Reunião de Teste")
                    .descricao(Optional.of("Descrição do evento de teste"))
                    .dataHoraInicio(Optional.of(now()))
                    .dataFim(Optional.empty())
                    .subEventos(List.of())
                    .idLocalExistente(Optional.empty())
                    .local(Optional.of(local))
                    .build();

            var response = EventoResponseDTO.builder()
                    .titulo("Reunião de Teste")
                    .descricao("Descrição do evento de teste")
                    .dataHoraInicio(request.dataHoraInicio().get())
                    .dataHoraFim(request.dataHoraInicio().get().plusMinutes(30))
                    .local(toLocalModel(local))
                    .subEventos(List.of())
                    .build();

            when(service.createEvent(request)).thenReturn(response);

            mockMvc.perform(post("/api/evento")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(request)))
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

            verify(service, times(1)).createEvent(request);
        }
    }
}