package edu.kmaooad.services.implementations;

import edu.kmaooad.apiCommunication.OrgsWebClient;
import edu.kmaooad.exceptions.IncorrectResourceParamsException;
import edu.kmaooad.models.AccessRule;
import edu.kmaooad.models.IssuerType;
import edu.kmaooad.models.ResourceType;
import edu.kmaooad.services.interfaces.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccessCheckServiceImpl implements AccessCheckService {

    private final BanUserService banUserService;
    private final BanDepartmentService banDepartmentService;
    private final BanOrganizationService banOrganizationService;
    private final AccessRuleService accessRuleService;
    private final OrgsWebClient orgsWebClient;
    private final ResourceService resourceService;


    @Override
    public boolean hasAccess(Long userId, Long realResourceId, ResourceType resourceType, Long commandId)
            throws IncorrectResourceParamsException {
        Long resourceId = resourceService.getResourceByRealIdAndType(realResourceId, resourceType).getId();
        return hasAccess(userId, resourceId, commandId);
    }

    @Override
    public boolean hasAccess(Long userId, Long resourceId, Long commandId) throws IncorrectResourceParamsException {
        if (banUserService.isUserBanned(userId)) {
            return false;
        }

        Optional<AccessRule> userAccessRule = accessRuleService
                .getById(userId, IssuerType.USER, resourceId, commandId);
        if (userAccessRule.isPresent()) {
            return userAccessRule.get().isAllowed();
        }

        Long departmentId = getUserDepartmentId(userId);
        if (banDepartmentService.isDepartmentBanned(departmentId)) {
            return false;
        }

        Optional<AccessRule> depAccessRule = accessRuleService
                .getById(departmentId, IssuerType.DEPARTMENT, resourceId, commandId);
        if (depAccessRule.isPresent()) {
            return depAccessRule.get().isAllowed();
        }

        Long organisationId = getUserOrganisationId(userId);
        if (banOrganizationService.isOrganizationBanned(organisationId)) {
            return false;
        }

        Optional<AccessRule> orgAccessRule = accessRuleService
                .getById(organisationId, IssuerType.ORGANIZATION, resourceId, commandId);

        return orgAccessRule.map(AccessRule::isAllowed).orElse(false);
    }
    private Long getUserDepartmentId(Long userId) throws IncorrectResourceParamsException {
        Long realDepId = orgsWebClient.fetchUserDepartments(userId);
        return resourceService.getResourceByRealIdAndType(realDepId, ResourceType.DEPARTMENT).getId();
    }

    private Long getUserOrganisationId(Long userId) throws IncorrectResourceParamsException {
        Long realOrgId = orgsWebClient.fetchUserOrganizations(userId);
        return resourceService.getResourceByRealIdAndType(realOrgId, ResourceType.ORGANIZATION).getId();
    }

}
