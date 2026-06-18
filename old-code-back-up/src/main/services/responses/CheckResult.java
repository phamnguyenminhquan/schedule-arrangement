package main.services.responses;

public class CheckResult {
    private final boolean valid;
    private final String message;
    private final ErrorType errorType;

    private CheckResult(boolean valid, String message, ErrorType errorType) {
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

    public static CheckResult success() {
        return new CheckResult(true, null, null);
    }

    public static CheckResult fail(String message, ErrorType errorType) {
        return new CheckResult(false, message, errorType);
    }
}
