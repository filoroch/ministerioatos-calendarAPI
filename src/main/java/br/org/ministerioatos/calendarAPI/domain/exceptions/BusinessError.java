package br.org.ministerioatos.calendarAPI.domain.exceptions;

public class BusinessError extends RuntimeException {
    public BusinessError(String message) {
        super(message);
    }
}
