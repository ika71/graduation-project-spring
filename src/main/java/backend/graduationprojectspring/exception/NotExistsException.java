package backend.graduationprojectspring.exception;

/**
 * 자원에 대한 동작을 시도할 때 그 자원이 존재하지 않을 경우 발생되는 예외다.<br>
 * message에 설정된 내용은 컨트롤러 밖으로 반환된다.
 */
public class NotExistsException extends CustomRunTimeException{
    public NotExistsException() {
    }

    public NotExistsException(String message) {
        super(message);
    }

    public NotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistsException(Throwable cause) {
        super(cause);
    }

    public NotExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
