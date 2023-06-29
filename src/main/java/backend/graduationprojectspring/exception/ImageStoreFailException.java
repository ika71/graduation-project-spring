package backend.graduationprojectspring.exception;

public class ImageStoreFailException extends RuntimeException {
    public ImageStoreFailException() {
    }

    public ImageStoreFailException(String message) {
        super(message);
    }

    public ImageStoreFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public ImageStoreFailException(Throwable cause) {
        super(cause);
    }

    public ImageStoreFailException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
