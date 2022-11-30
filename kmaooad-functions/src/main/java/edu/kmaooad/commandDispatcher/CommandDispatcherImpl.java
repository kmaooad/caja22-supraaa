package edu.kmaooad.commandDispatcher;

import edu.kmaooad.commandHandlers.CommandHandler;
import edu.kmaooad.processing.CommandCall;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class CommandDispatcherImpl implements CommandDispatcher {

        Map<Long, CommandHandler> handlers;

        public void dispatch(CommandCall commandCall) {
            // todo: check access rule for the command
            handlers.get(commandCall.getCommandId()).handle(commandCall);
        }

}
