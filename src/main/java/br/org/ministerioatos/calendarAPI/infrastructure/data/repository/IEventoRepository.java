package br.org.ministerioatos.calendarAPI.infrastructure.data.repository;

import br.org.ministerioatos.calendarAPI.infrastructure.data.models.EventDataJpa;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface IEventoRepository extends JpaRepository<EventDataJpa, Integer>, JpaSpecificationExecutor<EventDataJpa>
{

    boolean existsByTitleAndStartDateTime(@NotBlank String title, LocalDateTime localDateTime);

    boolean existsByStartDateTime(LocalDateTime startDateTime);
}
