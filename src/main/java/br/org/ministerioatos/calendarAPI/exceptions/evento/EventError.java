package br.org.ministerioatos.calendarAPI.exceptions.evento;

import br.org.ministerioatos.calendarAPI.exceptions.BusinessError;

public class EventError extends BusinessError {
    public EventError(String message) {
        super(message);
    }
}
