package edu.kmaooad.services.implementations;

import edu.kmaooad.apiCommunication.OrgsWebClient;
import edu.kmaooad.models.AccessRule;
import edu.kmaooad.models.IssuerType;
import edu.kmaooad.services.interfaces.AccessCheckService;
import edu.kmaooad.services.interfaces.AccessRuleService;
import edu.kmaooad.services.interfaces.BanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccessCheckServiceImpl implements AccessCheckService {

    private final BanService banService;
    private final AccessRuleService accessRuleService;
    private final OrgsWebClient orgsWebClient;


    @Override
    public boolean hasAccess(Long userId, Long resourceId, Long commandId) {
        if (banService.isUserBanned(userId)) {
            return false;
        }

        Optional<AccessRule> userAccessRule = accessRuleService
                .getById(userId, IssuerType.USER, resourceId, commandId);
        if (userAccessRule.isPresent()) {
            return userAccessRule.get().isAllowed();
        }

        Long departmentId = getUserDepartmentId(userId);
        if (banService.isDepartmentBanned(departmentId)){
            return false;
        }

        Optional<AccessRule> depAccessRule = accessRuleService
                .getById(departmentId, IssuerType.DEPARTMENT, resourceId, commandId);
        if (depAccessRule.isPresent()) {
            return depAccessRule.get().isAllowed();
        }

        Long organisationId = getUserOrganisationId(userId);
        if (banService.isOrganizationBanned(organisationId)){
            return false;
        }

        Optional<AccessRule> orgAccessRule = accessRuleService
                .getById(organisationId, IssuerType.ORGANIZATION, resourceId, commandId);


        return orgAccessRule.map(AccessRule::isAllowed).orElse(false);
    }

    private Long getUserDepartmentId(Long userId) {
        return orgsWebClient.fetchUserDepartments(userId);
    }

    private Long getUserOrganisationId(Long userId) {
        return orgsWebClient.fetchUserOrganizations(userId);
    }

}