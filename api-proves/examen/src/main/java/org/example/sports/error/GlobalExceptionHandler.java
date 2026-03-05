package org.example.sports.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.jspecify.annotations.Nullable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final DateTimeFormatter TS = DateTimeFormatter
            .ofPattern("dd/MM/yyyy HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    private ProblemDetail pd(HttpStatusCode status, String detail, @Nullable String path) {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(status, detail);
        p.setProperty("timestamp", TS.format(Instant.now()));
        if (path != null) p.setProperty("path", path);
        return p;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ProblemDetail> handleNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(pd(HttpStatus.NOT_FOUND, ex.getMessage(), req.getRequestURI()));
    }

    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ProblemDetail> handleBusiness(BusinessRuleException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(pd(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI()));
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ProblemDetail> handleBadCredentials(BadCredentialsException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(pd(HttpStatus.UNAUTHORIZED, ex.getMessage(), req.getRequestURI()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ProblemDetail> handleConstraintViolation(ConstraintViolationException ex, HttpServletRequest req) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(pd(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getRequestURI()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ProblemDetail> handleIntegrity(DataIntegrityViolationException ex, HttpServletRequest req) {
        // Útil quan salta la unique constraint (duplicat athlete+event)
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(pd(HttpStatus.CONFLICT, "Conflicte de dades: registre duplicat o restricció violada", req.getRequestURI()));
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request
    ) {
        ProblemDetail p = ProblemDetail.forStatus(status);
        p.setTitle("Validation error");
        p.setDetail("One or more fields are invalid");
        p.setProperty("timestamp", TS.format(Instant.now()));

        Map<String, List<String>> errors = new LinkedHashMap<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
            errors.computeIfAbsent(fe.getField(), k -> new ArrayList<>())
                    .add(Objects.toString(fe.getDefaultMessage(), "Invalid"));
        }
        p.setProperty("errors", errors);
        return ResponseEntity.status(status).body(p);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            @Nullable Object body,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request
    ) {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(statusCode, ex.getMessage() == null ? "Unexpected error" : ex.getMessage());
        p.setProperty("timestamp", TS.format(Instant.now()));
        return ResponseEntity.status(statusCode).body(p);
    }
}
