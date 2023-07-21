package backend.graduationprojectspring.controller.exception;

import backend.graduationprojectspring.exception.DuplicateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CategoryController {
    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<?> duplicate(DuplicateException e){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
}
