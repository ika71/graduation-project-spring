package backend.graduationprojectspring.exception;

/**
 * 자원 검색 시 그 검색이 존재 하지 않을 때 발생하는 예외다.<br>
 * message에 설정된 내용은 컨트롤러 밖으로 반환된다.
 */
public class FindFailException extends RuntimeException{
    public FindFailException() {
    }

    public FindFailException(String message) {
        super(message);
    }

    public FindFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public FindFailException(Throwable cause) {
        super(cause);
    }

    public FindFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
