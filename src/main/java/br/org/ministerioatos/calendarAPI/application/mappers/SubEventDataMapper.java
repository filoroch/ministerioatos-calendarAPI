package br.org.ministerioatos.calendarAPI.application.mappers;

import br.org.ministerioatos.calendarAPI.application.DTO.SubEventDTO;
import br.org.ministerioatos.calendarAPI.domain.objectValue.SubEvent;
import br.org.ministerioatos.calendarAPI.infrastructure.data.models.SubEventDataJpa;

public class SubEventDataMapper {
    static SubEvent toDomain(SubEventDTO input) {
        return new SubEvent(input.title(), input.date());
    }
    static SubEventDataJpa toDataJpa(SubEvent domain) {
        return new SubEventDataJpa(domain.getTitle(), domain.getData());
    }
    static SubEventDTO toOutput(SubEventDataJpa dataJpa) {
        return new SubEventDTO(dataJpa.getTitle(), dataJpa.getDate());
    }
}
