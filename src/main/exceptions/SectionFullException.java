package main.exceptions;

public class SectionFullException extends RuntimeException {
    public SectionFullException(String message) {
        super(message);
    }
}
