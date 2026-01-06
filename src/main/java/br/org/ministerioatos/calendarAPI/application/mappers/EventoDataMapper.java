package br.org.ministerioatos.calendarAPI.application.mappers;

import br.org.ministerioatos.calendarAPI.application.input.CreateEventInput;
import br.org.ministerioatos.calendarAPI.application.output.EventOutput;
import br.org.ministerioatos.calendarAPI.domain.entity.Event;
import br.org.ministerioatos.calendarAPI.infrastructure.data.models.EventDataJpa;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class EventoDataMapper {
    public static Event toDomain(CreateEventInput input) {
        return new Event(
            input.title(),
            input.description(),
            input.startDateTime(),
            input.endDateTime(),

            input.subEvents().stream()
                    .map(SubEventDataMapper::toDomain)
                    .toList(),

            AdressDataMapper.toDomain(input.adress())
        );
    }
    public static EventDataJpa toDataJpa(Event domain) {
        return new EventDataJpa(
            domain.getTitle(),
            domain.getDescription(),
            domain.getStartDateTime(),
            domain.getEndDateTime(),

            domain.getSubEvents().stream()
                    .map(SubEventDataMapper::toDataJpa)
                    .toList(),

            AdressDataMapper.toDataJpa(domain.getAdress())
        );
    }
    public EventOutput toOutput(EventDataJpa dataJpa) {
        return new EventOutput(
            dataJpa.getId(),
            dataJpa.getTitle(),
            dataJpa.getDescription(),
            new HashMap<>() {{
                put("start", dataJpa.getStartDateTime().toString());
                put("end", dataJpa.getEndDateTime().toString());
            }},
            dataJpa.getSubEvents().stream().map(SubEventDataMapper::toOutput).toList(),
            AdressDataMapper.toOutput(dataJpa.getAdress())
        );
    }

//    static CreateEventOutput toOutput(Evento domain) {
//        return new CreateEventOutput(
//            domain.getId(),
//            domain.getTitle(),
//            domain.getDescription(),
//            domain.getDate().toString(),
//            domain.getTime().toString(),
//            domain.getLocation()
//        );
//    }
}
