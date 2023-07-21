package backend.graduationprojectspring.exception;

/**
 * 이미지 저장 시 Io 예외가 발생될 때 일어나는 예외다.<br>
 * message 내용은 컨트롤러 밖으로 반환된다.
 */
public class ImageStoreFailException extends CustomRunTimeException {
    public ImageStoreFailException(String message) {
        super(message);
    }

    public ImageStoreFailException(String message, Throwable cause) {
        super(message, cause);
    }
}
