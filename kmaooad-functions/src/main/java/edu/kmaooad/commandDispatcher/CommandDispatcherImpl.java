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
        // todo: check access rule for the command
        //  accessCheckService.hasAccess(commandCall.getUserId(),commandCall.getResourceId(), commandCall.getResourceType(), commandCall.getCommandId())
        if (!accessCheckService.hasAccess(commandCall.getUserId(), 0L, ResourceType.USER, commandCall.getCommandId())) {
            throw new AccessDeniedException();
        }
        handlers.get(commandCall.getCommandId()).handle(commandCall);
    }

}
