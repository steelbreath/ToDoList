package ua.org.shpp.todolist.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Locale;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({TaskNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ProblemDetail> handleTaskNotFoundException(TaskNotFoundException ex, WebRequest request) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()));
        detail.setInstance(URI.create(request.getDescription(false)));
        detail.setProperty("timestamp", LocalDateTime.now());
        return ResponseEntity.of(detail).build();
    }

    @ExceptionHandler({UserNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ResponseEntity<ProblemDetail> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND,
                messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()));
        detail.setInstance(URI.create(request.getDescription(false)));
        detail.setProperty("timestamp", LocalDateTime.now());
        return ResponseEntity.of(detail).build();
    }

    @ExceptionHandler({IllegalDataChangeException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ProblemDetail> handleIllegalStatusChangeException(IllegalDataChangeException ex, WebRequest request) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()));
        detail.setInstance(URI.create(request.getDescription(false)));
        detail.setProperty("timestamp", LocalDateTime.now());
        return ResponseEntity.of(detail).build();
    }

    @ExceptionHandler({UsernameAlreadyExistException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResponseEntity<ProblemDetail> handleUsernameAlreadyExistException(UsernameAlreadyExistException ex, WebRequest request) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
                messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale()));
        detail.setInstance(URI.create(request.getDescription(false)));
        detail.setProperty("timestamp", LocalDateTime.now());
        return ResponseEntity.of(detail).build();
    }
}
