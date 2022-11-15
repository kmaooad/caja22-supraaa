package edu.kmaooad.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String objectName) {
        super("Object '" + objectName +"' not found by id");
    }
}
