package br.org.ministerioatos.calendarAPI.module.Evento.repository;

import br.org.ministerioatos.calendarAPI.module.Evento.DTOs.response.EventoResponseDTO;
import br.org.ministerioatos.calendarAPI.module.Evento.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

import java.util.function.Function;

public interface IEventoRepository extends JpaRepository<Evento, Integer>, JpaSpecificationExecutor<Evento>
{
    List<Evento> findByTituloAndDataHoraInicio(String titulo, LocalDateTime dataHoraInicio);
}
