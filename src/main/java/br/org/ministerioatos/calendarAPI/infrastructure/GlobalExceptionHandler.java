package br.org.ministerioatos.calendarAPI.infrastructure;

import br.org.ministerioatos.calendarAPI.exceptions.BusinessError;
import br.org.ministerioatos.calendarAPI.exceptions.evento.EventAlredyExists;
import br.org.ministerioatos.calendarAPI.exceptions.local.LocalAlredyExists;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/// Essa classe permitirá tratar exceções de forma global na aplicação.
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({EventAlredyExists.class, LocalAlredyExists.class})
    public ProblemDetail handleConflictBusinessErrors(BusinessError ex) {
        ProblemDetail problem = ProblemDetail.forStatus(409);
        problem.setTitle(ex.getClass().getSimpleName());
        problem.setDetail(ex.getMessage());
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleUnexpected(Exception ex) {

        ProblemDetail problem = ProblemDetail.forStatus(500);
        problem.setTitle("Unexpected error");
        problem.setDetail("Unexpected error occurred");

        return problem;
    }
}
