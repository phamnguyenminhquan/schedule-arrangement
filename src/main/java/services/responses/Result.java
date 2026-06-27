package services.responses;

public class Result {
    private final boolean valid;
    private final String message;
    private final ErrorType errorType;

    private Result(boolean valid, String message, ErrorType errorType) {
        this.valid = valid;
        this.message = message;
        this.errorType = errorType;
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

    public static Result success() {
        return new Result(true, null, null);
    }

    public static Result fail(String message, ErrorType errorType) {
        return new Result(false, message, errorType);
    }
}
