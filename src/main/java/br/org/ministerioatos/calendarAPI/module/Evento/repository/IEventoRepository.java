package br.org.ministerioatos.calendarAPI.module.Evento.repository;

import br.org.ministerioatos.calendarAPI.module.Evento.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

import java.util.function.Function;

public interface IEventoRepository extends JpaRepository<Evento, Integer>
{
    List<Evento> findByTituloAndDataHoraInicio(String titulo, LocalDateTime dataHoraInicio);
}
