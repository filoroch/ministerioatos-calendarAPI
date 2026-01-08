package br.org.ministerioatos.calendarAPI.domain.exceptions;

public class UsernameAlredyExistsException extends RuntimeException {
    public UsernameAlredyExistsException(String message) {
        super(message);
    }
}
