package backend.graduationprojectspring.controller.exception;

import backend.graduationprojectspring.exception.HttpError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception e){
        log.error("error", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValid(MethodArgumentNotValidException e){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .build();
    }

    @ExceptionHandler(HttpError.class)
    public ResponseEntity<?> customRunTimeException(HttpError error){
        if(error.getHttpStatus().value() >= 500){
            log.error("error", error);
        }

        return ResponseEntity
                .status(error.getHttpStatus())
                .body(error.getMessage());
    }
}
