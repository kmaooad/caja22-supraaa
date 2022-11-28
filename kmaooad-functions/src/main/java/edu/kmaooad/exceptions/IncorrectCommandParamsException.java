package edu.kmaooad.exceptions;

public class IncorrectCommandParamsException extends Exception{

    public IncorrectCommandParamsException(String field, String value) {
        super("Command field '" + field +"' cannot have value: " + value);
    }

}
