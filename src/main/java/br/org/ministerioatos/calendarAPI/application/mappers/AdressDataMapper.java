package br.org.ministerioatos.calendarAPI.application.mappers;

import br.org.ministerioatos.calendarAPI.application.input.AdressInput;
import br.org.ministerioatos.calendarAPI.application.output.AdressOutput;
import br.org.ministerioatos.calendarAPI.domain.objectValue.Adress;
import br.org.ministerioatos.calendarAPI.domain.State;
import br.org.ministerioatos.calendarAPI.infrastructure.data.models.AdressDataJpa;

public class AdressDataMapper {
    static Adress toDomain(AdressInput input) {
        return new Adress(
            input.street(),
            input.number(),
            input.neighborhood(),
            input.city(),
            State.valueOf(input.state().toLowerCase()),
            input.zipCode()
        );
    }

    static AdressDataJpa toDataJpa(Adress domain){
        return new AdressDataJpa(
            domain.getStreet(),
            domain.getNumber(),
            domain.getNeighborhood(),
            domain.getCity(),
            domain.getState().name(),
            domain.getZipCode()
        );
    }

    public static AdressOutput toOutput(AdressDataJpa adress) {
        return new AdressOutput(
            adress.getStreet(),
            adress.getNumber(),
            adress.getNeighborhood(),
            adress.getCity(),
            adress.getState(),
            adress.getZipCode()
        );
    }
}
