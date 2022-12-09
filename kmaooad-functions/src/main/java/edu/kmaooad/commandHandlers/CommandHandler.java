package edu.kmaooad.commandHandlers;

import edu.kmaooad.processing.CommandCall;

public interface CommandHandler {

    void handle(CommandCall commandCall) throws Exception;

}
