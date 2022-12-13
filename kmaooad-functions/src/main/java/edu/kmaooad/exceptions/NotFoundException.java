package edu.kmaooad.exceptions;

public class NotFoundException extends RuntimeException {

    public NotFoundException(String objectName) {
        super("Object '" + objectName +"' not found by id");
    }

    public NotFoundException(String objectName, String searchedBy) {
        super("Object '" + objectName +"' not found by "+searchedBy);
    }
}
