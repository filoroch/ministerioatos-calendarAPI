package br.org.ministerioatos.calendarAPI.exceptions.evento;

public class EventAlredyExists extends EventError {

    public EventAlredyExists() {
        super("Já existe um evento com esse título e data de início");
    }

    public EventAlredyExists(String message) {
        super(message);
    }
}
