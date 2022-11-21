package edu.kmaooad.exceptions;

public class ResourceCompositeFieldNotUniqueException extends Exception{

    public ResourceCompositeFieldNotUniqueException() {
        super("Resource with such composite field already exists!");
    }

}
