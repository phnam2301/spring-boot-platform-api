package vn.chuot96.verify3rd.exception;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Object> handleInvalidToken(InvalidTokenException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Invalid raw", ex.getMessage());
    }

    @ExceptionHandler(NoSupportedProviderException.class)
    public ResponseEntity<Object> handleNoSupportedProvider(NoSupportedProviderException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, "Unsupported party", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneralError(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error", ex.getMessage());
    }

    private ResponseEntity<Object> buildResponse(HttpStatus status, String error, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", status.value());
        body.put("error", error);
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}
