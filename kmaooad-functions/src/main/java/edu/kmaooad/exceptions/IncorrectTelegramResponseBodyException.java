package edu.kmaooad.exceptions;

public class IncorrectTelegramResponseBodyException extends RuntimeException {
    public IncorrectTelegramResponseBodyException() {
        super("Telegram response body is not correct!");
    }
}
