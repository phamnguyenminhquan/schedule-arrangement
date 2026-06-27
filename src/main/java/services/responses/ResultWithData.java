package services.responses;

public class ResultWithData<T> {
    private final boolean valid;
    private final String message;
    private final ErrorType errorType;
    private final T data;

    private ResultWithData(boolean valid, String message, ErrorType errorType, T data) {
        this.valid = valid;
        this.message = message;
        this.errorType = errorType;
        this.data = data;
    }

    public boolean isValid() {
        return valid;
    }

    public String getMessage() {
        return message;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public T getData() {
        return data;
    }

    public static <T> ResultWithData<T> success(T data) {
        return new ResultWithData<T>(true, null, null, data);
    }

    public static <T> ResultWithData<T> fail(String message, ErrorType errorType) {
        return new ResultWithData<T>(false, message, errorType, null);
    }
}
