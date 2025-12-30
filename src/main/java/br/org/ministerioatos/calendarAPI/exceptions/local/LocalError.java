package br.org.ministerioatos.calendarAPI.exceptions.local;

import br.org.ministerioatos.calendarAPI.exceptions.BusinessError;

public class LocalError extends BusinessError {
    public LocalError(String message) {
        super(message);
    }
}
