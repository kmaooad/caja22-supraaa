package edu.kmaooad.exceptions;

public class IncorrectRequestBodyException extends RuntimeException {
    public IncorrectRequestBodyException() {
        super("Request body is not correct");
    }
}
