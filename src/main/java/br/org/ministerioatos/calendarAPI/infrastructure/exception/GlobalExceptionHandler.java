package br.org.ministerioatos.calendarAPI.infrastructure.exception;

import br.org.ministerioatos.calendarAPI.domain.exceptions.BusinessError;
import br.org.ministerioatos.calendarAPI.domain.exceptions.UsernameAlredyExistsException;
import br.org.ministerioatos.calendarAPI.domain.exceptions.evento.EventAlredyExists;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/// Essa classe permitirá tratar exceções de forma global na aplicação.
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    // TODO: testar se o exception handler está funcionando corretamente com o postman e o scalar

    @ExceptionHandler({EventAlredyExists.class, UsernameAlredyExistsException.class})
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
