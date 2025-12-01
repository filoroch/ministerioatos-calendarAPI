package br.org.ministerioatos.calendarAPI.module.Evento.repository;

import br.org.ministerioatos.calendarAPI.module.Evento.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IEventoRepository extends JpaRepository<Evento, Integer>
{

}
