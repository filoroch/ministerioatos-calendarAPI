package br.org.ministerioatos.calendarAPI.module.Evento.service;

import br.org.ministerioatos.calendarAPI.exceptions.EventAlredyExists;
import br.org.ministerioatos.calendarAPI.exceptions.EventError;
import br.org.ministerioatos.calendarAPI.module.Evento.DTOs.request.EventoRequestDTO;
import br.org.ministerioatos.calendarAPI.module.Evento.DTOs.request.SubEventoRequestDTO;
import br.org.ministerioatos.calendarAPI.module.Evento.DTOs.response.EventoResponseDTO;
import br.org.ministerioatos.calendarAPI.module.Evento.DTOs.response.SubEventoResponseDTO;
import br.org.ministerioatos.calendarAPI.module.Evento.model.Evento;
import br.org.ministerioatos.calendarAPI.module.Evento.model.SubEvento;
import br.org.ministerioatos.calendarAPI.module.Evento.repository.IEventoRepository;
import br.org.ministerioatos.calendarAPI.module.Local.DTO.LocalRequestDTO;
import br.org.ministerioatos.calendarAPI.module.Local.model.Local;
import br.org.ministerioatos.calendarAPI.module.Local.service.LocalService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.org.ministerioatos.calendarAPI.module.Local.service.LocalService.toLocalModel;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventoServiceTest {

    @InjectMocks
    private EventoService service;

    @Mock
    private IEventoRepository repository;

    @Mock
    private LocalService localService;

    @Nested
    @DisplayName("Criar eventos")
    class createEventTests {

        @Test
        @DisplayName("Deve criar um evento com todos os parametros")
        void shouldCreateEventWithAllParameters() {

            List subevents = new ArrayList<SubEventoRequestDTO>();

            var local = LocalRequestDTO.builder()
                    .rua("Rua das Flores")
                    .numero(123)
                    .complemento("Apto 45")
                    .cidade("São Paulo")
                    .bairro("Jardins")
                    .CEP("01234-567")
                    .UF("SP")
                    .build();

            // Evento a ser criado
            var dto = EventoRequestDTO.builder()
                    .titulo("Culto de Familia")
                    .descricao(Optional.of("Culto principal que reune todo o ministerio. Todos os domingos as 19h"))
                    .dataHoraInicio(Optional.of(LocalDateTime.of(2025, 11, 30, 19, 0)))
                    .dataFim(Optional.of(LocalDateTime.of(2025, 11, 30, 21, 0)))
                    .subEventos(subevents)
                    .idLocalExistente(Optional.empty())
                    .local(Optional.of(local))
                    .build();

            // Evento esperado após a criação
            var event = new Evento().builder()
                    .id(1)
                    .titulo("Culto de Familia")
                    .descricao("Culto principal que reune todo o ministerio. Todos os domingos as 19h")
                    .dataHoraInicio(LocalDateTime.of(2025, 11, 30, 19, 0))
                    .dataHoraFim(LocalDateTime.of(2025, 11, 30, 21, 0))
                    .subEventos(new ArrayList<SubEvento>())
                    .local(localService.toLocalModel(local))
                    .build();

            when(repository.save(ArgumentMatchers.any(Evento.class))).thenReturn(event);

            var result = service.createEvent(dto);

            assertNotNull(result, "Resultado não deve ser nulo");
            assertInstanceOf(EventoResponseDTO.class, result, "Deve retornar um EventoResponseDTO");
            assertEquals(event.getTitulo(), result.titulo(), "Titulo deve ser igual");
            assertEquals(event.getDescricao(), result.descricao(), "Descrição deve ser igual");
            assertEquals(event.getDataHoraInicio(), result.dataHoraInicio(), "Data de início deve ser igual");
            assertEquals(event.getDataHoraFim(), result.dataHoraFim(), "Data de fim deve ser igual");

            verify(repository).save(ArgumentMatchers.any(Evento.class));
        }

        @Test
        @DisplayName("Deve usar a data e hora atual caso não seja passado uma data de inicio e inferir 30 minutos na data fim")
        void shouldUseCurrentDateTimeIfStartDateNotProvidedAndInfer30MinutesToEndDate() {

            List subevents = new ArrayList<SubEventoRequestDTO>();

            var local = LocalRequestDTO.builder()
                    .rua("Rua das Flores")
                    .numero(123)
                    .complemento("Apto 45")
                    .cidade("São Paulo")
                    .bairro("Jardins")
                    .CEP("01234-567")
                    .UF("SP")
                    .build();

            // Evento a ser criado
            var dto = EventoRequestDTO.builder()
                    .titulo("Culto de Familia")
                    .descricao(Optional.of("Culto principal que reune todo o ministerio. Todos os domingos as 19h"))
                    .dataHoraInicio(Optional.empty())
                    .dataFim(Optional.empty())
                    .subEventos(subevents)
                    .idLocalExistente(Optional.empty())
                    .local(Optional.of(local))
                    .build();

            // Evento esperado após a criação
            var event = new Evento().builder()
                    .id(1)
                    .titulo("Culto de Familia")
                    .descricao("Culto principal que reune todo o ministerio. Todos os domingos as 19h")
                    .dataHoraInicio(LocalDateTime.now())
                    .dataHoraFim(LocalDateTime.now().plusMinutes(30))
                    .subEventos(new ArrayList<SubEvento>())
                    .local(localService.toLocalModel(local))
                    .build();

            when(repository.save(ArgumentMatchers.any(Evento.class))).thenReturn(event);

            var result = service.createEvent(dto);

            assertNotNull(result, "Resultado não deve ser nulo");
            assertInstanceOf(EventoResponseDTO.class, result, "Deve retornar um EventoResponseDTO");
            assertEquals(event.getTitulo(), result.titulo(), "Titulo deve ser igual");
            assertEquals(event.getDescricao(), result.descricao(), "Descrição deve ser igual");
            assertEquals(event.getDataHoraInicio(), result.dataHoraInicio(), "Data de início deve ser igual");
            assertEquals(event.getDataHoraFim(), result.dataHoraFim(), "Data de fim deve ser igual");

            verify(repository).save(ArgumentMatchers.any(Evento.class));
        }

        @Test
        @DisplayName("Deve criar um evento com parametros obrigatórios")
        void shouldCreateEventWithMandatoryParameters() {

            var local = LocalRequestDTO.builder()
                    .rua("Rua das Flores")
                    .numero(123)
                    .complemento("Apto 45")
                    .cidade("São Paulo")
                    .bairro("Jardins")
                    .CEP("01234-567")
                    .UF("SP")
                    .build();

            /// Evento com parametros minimos
            var minimalEvent = EventoRequestDTO.builder()
                    .titulo("Culto de Familia")
                    .descricao(Optional.empty())
                    .dataHoraInicio(Optional.empty())
                    .dataFim(Optional.empty())
                    .subEventos(new ArrayList<SubEventoRequestDTO>())
                    .idLocalExistente(Optional.empty())
                    .local(Optional.of(local))
                    .build();

            var event = new Evento().builder()
                    .id(1)
                    .titulo(minimalEvent.titulo())
                    .dataHoraInicio(LocalDateTime.now())
                    .dataHoraFim(LocalDateTime.now().plusMinutes(30))
                    .subEventos(new ArrayList<SubEvento>())
                    .local(toLocalModel(local))
                    .build();

            when(repository.save(ArgumentMatchers.any(Evento.class)))
                    .thenReturn(event);

            var expected = service.createEvent(minimalEvent);

            assertNotNull(expected, "Resultado não deve ser nulo");
            assertInstanceOf(EventoResponseDTO.class, expected, "Deve retornar um EventoResponse");
            assertEquals(minimalEvent.titulo(), expected.titulo(), "Titulo deve ser igual");
            assertNotEquals(minimalEvent.dataHoraInicio(), expected.dataHoraInicio(), "Data de início deve ser now quando não for passado");
            assertNotEquals(minimalEvent.dataFim(), expected.dataHoraFim(), "Data de fim deve ser now + 30 minutos quando não for passado");

            verify(repository, times(1)).save(ArgumentMatchers.any(Evento.class));
        }

        @Test
        @DisplayName("Não deve criar um evento com titulo e data inicial a de um evento já registrado")
        void shouldNotCreateEventWithTitleAndStartDateOfAnAlreadyRegisteredEvent() {

            var local = LocalRequestDTO.builder()
                    .rua("Rua das Flores")
                    .numero(123)
                    .complemento("Apto 45")
                    .cidade("São Paulo")
                    .bairro("Jardins")
                    .CEP("01234-567")
                    .UF("SP")
                    .build();

            /// Evento existente
            var event = new Evento().builder()
                    .id(1)
                    .titulo("Culto de Familia")
                    .dataHoraInicio(LocalDateTime.of(2025, 11, 30, 19, 0))
                    .subEventos(new ArrayList<SubEvento>())
                    .local(toLocalModel(local))
                    .build();

            /// Evento a ser adicionado
            var newEvent = EventoRequestDTO.builder()
                    .titulo("Culto de Familia")
                    .dataHoraInicio(Optional.of(LocalDateTime.of(2025, 11, 30, 19, 0)))
                    .subEventos(new ArrayList<SubEventoRequestDTO>())
                    .local(Optional.of(local))
                    .build();

            /// Chamada do repositorio mockado
            when(repository.findByTituloAndDataHoraInicio(newEvent.titulo(), newEvent.dataHoraInicio().get()))
                    .thenReturn(List.of(event));

            var expected = assertThrows(
                    EventAlredyExists.class,
                    () -> { service.createEvent(newEvent); },
                    "Deveria lançar uma exceção de evento já existente");

            assertNotNull(expected.getMessage(), "Mensagem de erro não deve ser nula");

            verify(repository, never()).save(ArgumentMatchers.any(Evento.class));
        }

    }
}