package br.org.ministerioatos.calendarAPI.exceptions.local;

public class LocalAlredyExists extends LocalError{
    public LocalAlredyExists() {
        super("Já existe um local com esse nome");
    }

    public LocalAlredyExists(String message) {
        super(message);
    }
}
