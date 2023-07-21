package backend.graduationprojectspring.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("문제가 발생하였습니다.");
    }
}
