package br.org.ministerioatos.calendarAPI.module.Evento.repository;

import br.org.ministerioatos.calendarAPI.module.Evento.model.Evento;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class EventoSpecification {
    public static Specification<Evento> containsTitle(String title){
        return (root, query, criteriaBuilder) ->
                title == null || title.isEmpty() ? null : criteriaBuilder.like(criteriaBuilder.lower(root.get("titulo")), "%" + title.toLowerCase() + "%"
        );
    }

    public static Specification<Evento> isDateInRange(LocalDateTime start, LocalDateTime end){
        return (root, query, criteriaBuilder) -> {
            if (start == null || end == null) {
                return null;
            }

            return criteriaBuilder.and(
                    criteriaBuilder.lessThanOrEqualTo(root.get("dataHoraInicio"), end),
                    criteriaBuilder.greaterThanOrEqualTo(root.get("dataHoraFim"), start)
            );
        };
    }
}
