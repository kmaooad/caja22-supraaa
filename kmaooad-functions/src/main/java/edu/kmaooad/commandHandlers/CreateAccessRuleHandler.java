package edu.kmaooad.commandHandlers;

import edu.kmaooad.events.HandlerEvent;
import edu.kmaooad.models.Command;
import edu.kmaooad.models.IssuerType;
import edu.kmaooad.models.Resource;
import edu.kmaooad.models.ResourceType;
import edu.kmaooad.processing.CommandCall;
import edu.kmaooad.services.interfaces.AccessRuleService;
import edu.kmaooad.services.interfaces.CommandService;
import edu.kmaooad.services.interfaces.ResourceService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateAccessRuleHandler implements CommandHandler {

    private AccessRuleService accessRuleService;
    private CommandService commandService;
    private ResourceService resourceService;
    private ApplicationEventPublisher applicationEventPublisher;

    /*

    command looks something like:

    createAccessRule <issuerId> <issuerType> <realResourceId> <realResourceType> <commandText> <allow|deny>

     */

    @Override
    public void handle(CommandCall commandCall) throws Exception {
        String[] args = commandCall.getArgs();
        Long issuerId = Long.parseLong(args[0]);
        IssuerType issuerType = IssuerType.valueOf(args[1]);
        Long realResourceId = Long.parseLong(args[2]);
        ResourceType realResourceType = ResourceType.valueOf(args[3]);
        String commandText = args[4];
        boolean allow = Boolean.parseBoolean(args[5]);
        Command command = commandService.getCommandByName(commandText);
        Resource resource = resourceService.getResourceByRealIdAndType(realResourceId, realResourceType);
        accessRuleService.upsert(issuerId, issuerType, resource.getId(), command.getId(), allow);
        applicationEventPublisher.publishEvent(new HandlerEvent("Access rule created", commandCall.getChatId()));
    }

}
