package edu.kmaooad.exceptions;

public class IncorrectResourceParamsException extends Exception {

    public IncorrectResourceParamsException(String field, String value) {
        super("Command field '" + field +"' cannot have value: " + value);
    }

}
