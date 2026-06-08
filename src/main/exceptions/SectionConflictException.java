package main.exceptions;

public class SectionConflictException extends RuntimeException {
    public SectionConflictException(String message) {
        super(message);
    }
}
