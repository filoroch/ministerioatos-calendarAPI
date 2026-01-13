package br.org.ministerioatos.calendarAPI.domain.exceptions;

public class UsernameAlredyExistsException extends BusinessError {
    public UsernameAlredyExistsException(String message) {
        super(message);
    }
}
