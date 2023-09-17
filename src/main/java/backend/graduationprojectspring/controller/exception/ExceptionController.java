package backend.graduationprojectspring.controller.exception;

import backend.graduationprojectspring.exception.CustomRunTimeException;
import backend.graduationprojectspring.exception.DuplicateException;
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

    @ExceptionHandler(CustomRunTimeException.class)
    public ResponseEntity<?> customRunTimeException(CustomRunTimeException e){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<?> duplicateException(CustomRunTimeException e){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
}
