package br.org.ministerioatos.calendarAPI.domain.exceptions.evento;

import br.org.ministerioatos.calendarAPI.domain.exceptions.BusinessError;

public class EventError extends BusinessError {
    public EventError(String message) {
        super(message);
    }
}
