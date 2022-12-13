package edu.kmaooad.commandHandlers;

import edu.kmaooad.events.HandlerEvent;
import edu.kmaooad.models.IssuerType;
import edu.kmaooad.processing.CommandCall;
import edu.kmaooad.services.interfaces.BanDepartmentService;
import edu.kmaooad.services.interfaces.BanOrganizationService;
import edu.kmaooad.services.interfaces.BanUserService;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UnbanHandler implements CommandHandler{

    private BanUserService banUserService;
    private BanDepartmentService banDepartmentService;
    private BanOrganizationService banOrganizationService;
    private ApplicationEventPublisher applicationEventPublisher;

    /*
    *
    * Command:
    *
    * unban <issuerType> <issuerId>
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
                if(!banUserService.isUserBanned(issuerId)) {
                    throw new Exception("This user is not banned!");
                } else {
                    banUserService.deleteBanForUser(issuerId);
                }
                break;
            case DEPARTMENT:
                issuer = "Department";
                if(!banDepartmentService.isDepartmentBanned(issuerId)) {
                    throw new Exception("This department is not banned!");
                } else {
                    banDepartmentService.deleteBanForDepartment(issuerId);
                }
                break;
            case ORGANIZATION:
                issuer = "Organization";
                if(!banOrganizationService.isOrganizationBanned(issuerId)) {
                    throw new Exception("This organization is not banned!");
                } else {
                    banOrganizationService.deleteBanForOrganization(issuerId);
                }
                break;
        }
        applicationEventPublisher.publishEvent(new HandlerEvent(issuer + " unbanned", commandCall.getChatId()));
    }

}