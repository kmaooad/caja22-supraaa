package edu.kmaooad.commandDispatcher;

import edu.kmaooad.commandHandlers.CommandHandler;
import edu.kmaooad.exceptions.AccessDeniedException;
import edu.kmaooad.models.ResourceType;
import edu.kmaooad.processing.CommandCall;
import edu.kmaooad.services.interfaces.AccessCheckService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@AllArgsConstructor
public class CommandDispatcherImpl implements CommandDispatcher {

    private Map<Long, CommandHandler> handlers;
    private AccessCheckService accessCheckService;

    public void dispatch(CommandCall commandCall) throws Exception {
        if (!accessCheckService.hasAccess(commandCall.getUserId(), Long.parseLong(commandCall.getArgs()[0]), ResourceType.valueOf(commandCall.getArgs()[1]), commandCall.getCommandId())) {
            throw new AccessDeniedException();
        }
        handlers.get(commandCall.getCommandId()).handle(commandCall);
    }

}
