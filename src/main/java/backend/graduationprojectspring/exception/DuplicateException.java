package backend.graduationprojectspring.exception;

/**
 * 자원 생성 시 중복 조건에 위배될 때 발생되는 예외다.<br>
 * message에 설정된 내용은 컨트롤러 밖으로 반환된다.
 */
public class DuplicateException extends RuntimeException{
    public DuplicateException() {
    }

    public DuplicateException(String message) {
        super(message);
    }

    public DuplicateException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateException(Throwable cause) {
        super(cause);
    }

    public DuplicateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
