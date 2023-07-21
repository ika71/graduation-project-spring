package backend.graduationprojectspring.exception;

/**
 * 직접 정의한 예외는 해당 클래스를 상속한다.
 * message에 설정된 내용은 컨트롤러 밖으로 반환된다.
 */
public abstract class CustomRunTimeException extends RuntimeException{
    public CustomRunTimeException() {
    }

    public CustomRunTimeException(String message) {
        super(message);
    }

    public CustomRunTimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomRunTimeException(Throwable cause) {
        super(cause);
    }

    public CustomRunTimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
