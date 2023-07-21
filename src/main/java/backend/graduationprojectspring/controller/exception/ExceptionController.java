package backend.graduationprojectspring.controller.exception;

import backend.graduationprojectspring.exception.DuplicateException;
import backend.graduationprojectspring.exception.ImageStoreFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
                .status(HttpStatus.BAD_REQUEST)
                .body("문제가 발생하였습니다.");
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValid(MethodArgumentNotValidException e){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(processValidationError(e));
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<?> duplicate(DuplicateException e){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(e.getMessage());
    }
    @ExceptionHandler(ImageStoreFailException.class)
    public ResponseEntity<?> imageStoreFail(ImageStoreFailException e){
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }

    private String processValidationError(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();

        StringBuilder builder = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            builder.append("[");
            builder.append(fieldError.getField());
            builder.append("](은)는 ");
            builder.append(fieldError.getDefaultMessage());
            builder.append(" 입력된 값: [");
            builder.append(fieldError.getRejectedValue());
            builder.append("]\n");
        }

        return builder.toString();
    }
}
