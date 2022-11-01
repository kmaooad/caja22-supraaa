package edu.kmaooad.exceptions;

public class EmptyRequestBodyException extends Exception {

    public EmptyRequestBodyException() {
        super("Request body must not be empty");
    }

}
