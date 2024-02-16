package backend.graduationprojectspring.exception;

import org.springframework.http.HttpStatus;

/**
 * message에 설정된 내용은 컨트롤러 밖으로 반환된다.
 */
public class HttpError extends RuntimeException{
    private final String message;
    private final HttpStatus httpStatus;

    public HttpError(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public HttpError(String message, HttpStatus httpStatus, Throwable e) {
        super(message, e);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }
}
