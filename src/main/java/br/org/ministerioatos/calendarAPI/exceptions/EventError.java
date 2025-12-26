package br.org.ministerioatos.calendarAPI.exceptions;

public class EventError extends RuntimeException {
    public EventError(String message) {
        super(message);
    }
}
