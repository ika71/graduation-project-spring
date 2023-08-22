package backend.graduationprojectspring.controller.exception;

import backend.graduationprojectspring.exception.CustomRunTimeException;
import backend.graduationprojectspring.exception.DuplicateException;
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
    /**
     * TODO 잘못된 METHOD로 호출 시 405가 아니라 500번으로 반환되어버림
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> exception(Exception e){
        log.error("error", e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("문제가 발생하였습니다.");
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> notValid(MethodArgumentNotValidException e){
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(processValidationError(e));
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
