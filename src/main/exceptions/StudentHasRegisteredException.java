package main.exceptions;

public class StudentHasRegisteredException extends RuntimeException {
    public StudentHasRegisteredException(String message) {
        super(message);
    }
}
