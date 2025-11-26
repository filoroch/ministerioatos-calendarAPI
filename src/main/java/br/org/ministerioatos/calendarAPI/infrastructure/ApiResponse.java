package br.org.ministerioatos.calendarAPI.infrastructure;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiResponse<T> {
    private boolean success;
    private HttpStatus status;
    private String message;
    private T data;

    public ApiResponse(boolean success, HttpStatus status, String message, T data) {
        this.success = success;
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
