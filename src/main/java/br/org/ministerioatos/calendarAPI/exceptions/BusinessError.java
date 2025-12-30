package br.org.ministerioatos.calendarAPI.exceptions;

public class BusinessError extends RuntimeException {
    public BusinessError(String message) {
        super(message);
    }
}
