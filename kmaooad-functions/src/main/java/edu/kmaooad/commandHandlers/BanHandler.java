package edu.kmaooad.commandHandlers;

import edu.kmaooad.events.HandlerEvent;
import edu.kmaooad.models.IssuerType;
import edu.kmaooad.processing.CommandCall;
import edu.kmaooad.services.interfaces.BanService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class BanHandler implements CommandHandler{

    private BanService banService;
    private ApplicationEventPublisher applicationEventPublisher;

    /*
     *
     * Command:
     *
     * ban <issuerType> <issuerId>
     *
     * */

    @Override
    public void handle(CommandCall commandCall) throws Exception {
        String[] args = commandCall.getArgs();
        IssuerType issuerType = IssuerType.valueOf(args[0]);
        Long issuerId = Long.parseLong(args[1]);
        String issuer = "";
        switch (issuerType) {
            case USER:
                issuer = "User";
                if(banService.isUserBanned(issuerId)) {
                    throw new Exception("This user is already banned!");
                } else {
                    banService.addBanForUser(issuerId);
                }
                break;
            case DEPARTMENT:
                issuer = "Department";
                if(banService.isDepartmentBanned(issuerId)) {
                    throw new Exception("This department is already banned!");
                } else {
                    banService.addBanForDepartment(issuerId);
                }
                break;
            case ORGANIZATION:
                issuer = "Organization";
                if(banService.isOrganizationBanned(issuerId)) {
                    throw new Exception("This organization is already banned!");
                } else {
                    banService.addBanForOrganization(issuerId);
                }
                break;
        }
        applicationEventPublisher.publishEvent(new HandlerEvent(issuer + " banned", commandCall.getChatId()));
    }

}