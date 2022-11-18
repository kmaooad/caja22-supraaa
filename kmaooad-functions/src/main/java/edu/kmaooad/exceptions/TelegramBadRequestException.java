package edu.kmaooad.exceptions;

public class TelegramBadRequestException extends RuntimeException {

    public TelegramBadRequestException(String reason) {
        super("Telegram replied with 400 status code: " + reason);
    }

}
