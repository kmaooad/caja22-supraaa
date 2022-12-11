package edu.kmaooad.commandHandlers;

import edu.kmaooad.events.HandlerEvent;
import edu.kmaooad.exceptions.NotFoundException;
import edu.kmaooad.models.*;
import edu.kmaooad.processing.CommandCall;
import edu.kmaooad.services.interfaces.AccessRuleService;
import edu.kmaooad.services.interfaces.CommandService;
import edu.kmaooad.services.interfaces.ResourceService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UpdateAccessRuleHandler implements CommandHandler{

    private AccessRuleService accessRuleService;
    private CommandService commandService;
    private ResourceService resourceService;
    private ApplicationEventPublisher applicationEventPublisher;

    /*
    *
    * Command:
    *
    * updateAccessRule <issuerId> <issuerType> <realResourceId> <realResourceType> <commandText> <allow|deny>
    *
    * */

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
        if(!accessRuleService.existsById(issuerId, issuerType, resource.getId(), command.getId())){
            throw new NotFoundException("Access Rule");
        }
        accessRuleService.upsert(issuerId, issuerType, resource.getId(), command.getId(), allow);
        applicationEventPublisher.publishEvent(new HandlerEvent("Access rule updated", commandCall.getChatId()));
    }

}
