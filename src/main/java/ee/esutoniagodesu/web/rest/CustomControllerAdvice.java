package ee.esutoniagodesu.web.rest;

import org.apache.catalina.connector.ClientAbortException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice(annotations = RestController.class)
public class CustomControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomControllerAdvice.class);

    @ExceptionHandler
    public ResponseEntity<GenericMessage<String>> bad_request(Exception e) {
        log.error("bad_request {}", e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageOf(new RuntimeException("Serveri viga.")));
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<GenericMessage<String>> forbidden(Exception e) {
        log.error("forbidden {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(messageOf(e));
    }

    @ExceptionHandler({ClientAbortException.class})
    public ResponseEntity<GenericMessage<String>> connection_reset_by_peer(Exception e) {
        log.error("connection_reset_by_peer {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageOf(new RuntimeException(e.getMessage())));
    }

    @ExceptionHandler({IllegalStateException.class})
    public ResponseEntity<GenericMessage<String>> internal_server_error(Exception e) {
        log.error("internal_server_error {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messageOf(e));
    }

    private static GenericMessage<String> messageOf(Exception e) {
        String message = e.getMessage() != null ? e.getMessage() : "null";
        return new GenericMessage<>(message);
    }
}
