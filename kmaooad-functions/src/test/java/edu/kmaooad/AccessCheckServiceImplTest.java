package edu.kmaooad;

import edu.kmaooad.apiCommunication.OrgsWebClient;
import edu.kmaooad.models.AccessRule;
import edu.kmaooad.models.IssuerType;
import edu.kmaooad.models.Resource;
import edu.kmaooad.models.ResourceType;
import edu.kmaooad.services.implementations.AccessCheckServiceImpl;
import edu.kmaooad.services.interfaces.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class AccessCheckServiceImplTest {

    private final BanUserService banUserService = mock(BanUserService.class);
    private final BanDepartmentService banDepartmentService = mock(BanDepartmentService.class);
    private final BanOrganizationService banOrganizationService = mock(BanOrganizationService.class);
    private final AccessRuleService accessRuleService = mock(AccessRuleService.class);
    private final OrgsWebClient orgsWebClient = mock(OrgsWebClient.class);
    private final ResourceService resourceService = mock(ResourceService.class);

    private final AccessCheckService accessCheckService = new AccessCheckServiceImpl(banUserService, banDepartmentService,
            banOrganizationService, accessRuleService, orgsWebClient, resourceService);

    @Test
    @SneakyThrows
    void whenUserIsBannedThenAccessFalse_ByRealResourceId(){
        Long userId = 1L;
        Long resourceId = 2L;
        Long commandId = 3L;
        Long realDepId = 4L;
        ResourceType resourceType = ResourceType.DEPARTMENT;

        Resource resource = new Resource();
        resource.setId(resourceId);
        when(resourceService.getResourceByRealIdAndType(realDepId, resourceType)).thenReturn(resource);

        when(banUserService.isUserBanned(userId)).thenReturn(true);

        assertThat(accessCheckService.hasAccess(userId, realDepId, resourceType, commandId)).isFalse();
    }

    @Test
    @SneakyThrows
    void whenUserIsBannedThenAccessFalse(){
        Long userId = 1L;
        Long resourceId = 1L;
        Long commandId = 1L;
        when(banUserService.isUserBanned(userId)).thenReturn(true);

        assertThat(accessCheckService.hasAccess(userId, resourceId, commandId)).isFalse();
    }

    @Test
    @SneakyThrows
    void whenUserAccessDeniesThenAccessFalse(){
        Long userId = 1L;
        IssuerType issuerType = IssuerType.USER;
        Long resourceId = 1L;
        Long commandId = 1L;
        AccessRule userAccessRule = new AccessRule(userId, issuerType, resourceId, commandId, false);

        when(banUserService.isUserBanned(userId)).thenReturn(false);
        when(accessRuleService
                .getById(userId, issuerType, resourceId, commandId)).thenReturn(Optional.of(userAccessRule));

        assertThat(accessCheckService.hasAccess(userId, resourceId, commandId)).isFalse();
    }

    @Test
    @SneakyThrows
    void whenUserAccessAllowsThenAccessTrue(){
        Long userId = 1L;
        IssuerType issuerType = IssuerType.USER;
        Long resourceId = 1L;
        Long commandId = 1L;
        AccessRule userAccessRule = new AccessRule(userId, issuerType, resourceId, commandId, true);

        when(banUserService.isUserBanned(userId)).thenReturn(false);
        when(accessRuleService
                .getById(userId, issuerType, resourceId, commandId)).thenReturn(Optional.of(userAccessRule));

        assertThat(accessCheckService.hasAccess(userId, resourceId, commandId)).isTrue();
    }

    @Test
    @SneakyThrows
    void whenUserDepartmentIsBannedThenAccessFalse(){
        Long userId = 1L;
        IssuerType issuerType = IssuerType.USER;
        Long resourceId = 2L;
        Long commandId = 3L;

        when(banUserService.isUserBanned(userId)).thenReturn(false);
        when(accessRuleService
                .getById(userId, issuerType, resourceId, commandId)).thenReturn(Optional.empty());

        Long realDepId = 4L;
        Long departmentId = 5L;
        when(orgsWebClient.fetchUserDepartments(userId)).thenReturn(realDepId);

        Resource resource = new Resource();
        resource.setId(departmentId);
        when(resourceService.getResourceByRealIdAndType(realDepId, ResourceType.DEPARTMENT)).thenReturn(resource);
        when(banDepartmentService.isDepartmentBanned(departmentId)).thenReturn(true);

        assertThat(accessCheckService.hasAccess(userId, resourceId, commandId)).isFalse();
    }

    @Test
    @SneakyThrows
    void whenUserDepartmentAccessDeniesThenAccessFalse(){
        Long userId = 1L;
        IssuerType issuerType = IssuerType.USER;
        Long resourceId = 2L;
        Long commandId = 3L;

        when(banUserService.isUserBanned(userId)).thenReturn(false);
        when(accessRuleService
                .getById(userId, issuerType, resourceId, commandId)).thenReturn(Optional.empty());

        Long realDepId = 4L;
        Long departmentId = 5L;
        when(orgsWebClient.fetchUserDepartments(userId)).thenReturn(realDepId);

        Resource resource = new Resource();
        resource.setId(departmentId);
        when(resourceService.getResourceByRealIdAndType(realDepId, ResourceType.DEPARTMENT)).thenReturn(resource);
        when(banDepartmentService.isDepartmentBanned(departmentId)).thenReturn(false);

        AccessRule depAccessRule = new AccessRule(departmentId, issuerType, resourceId, commandId, false);
        when(accessRuleService
                .getById(departmentId, IssuerType.DEPARTMENT, resourceId, commandId)).thenReturn(Optional.of(depAccessRule));

        assertThat(accessCheckService.hasAccess(userId, resourceId, commandId)).isFalse();
    }

    @Test
    @SneakyThrows
    void whenUserDepartmentAccessAllowsThenAccessTrue(){
        Long userId = 1L;
        IssuerType issuerType = IssuerType.USER;
        Long resourceId = 2L;
        Long commandId = 3L;

        when(banUserService.isUserBanned(userId)).thenReturn(false);
        when(accessRuleService
                .getById(userId, issuerType, resourceId, commandId)).thenReturn(Optional.empty());

        Long realDepId = 4L;
        Long departmentId = 5L;
        when(orgsWebClient.fetchUserDepartments(userId)).thenReturn(realDepId);

        Resource resource = new Resource();
        resource.setId(departmentId);
        when(resourceService.getResourceByRealIdAndType(realDepId, ResourceType.DEPARTMENT)).thenReturn(resource);
        when(banDepartmentService.isDepartmentBanned(departmentId)).thenReturn(false);

        AccessRule depAccessRule = new AccessRule(departmentId, issuerType, resourceId, commandId, true);
        when(accessRuleService
                .getById(departmentId, IssuerType.DEPARTMENT, resourceId, commandId)).thenReturn(Optional.of(depAccessRule));

        assertThat(accessCheckService.hasAccess(userId, resourceId, commandId)).isTrue();
    }

    @Test
    @SneakyThrows
    void whenUserOrganisationIsBannedThenAccessFalse(){
        Long userId = 1L;
        IssuerType issuerType = IssuerType.USER;
        Long resourceId = 2L;
        Long commandId = 3L;

        when(banUserService.isUserBanned(userId)).thenReturn(false);
        when(accessRuleService
                .getById(userId, issuerType, resourceId, commandId)).thenReturn(Optional.empty());

        Long realDepId = 4L;
        Long departmentId = 5L;
        when(orgsWebClient.fetchUserDepartments(userId)).thenReturn(realDepId);

        Resource depResource = new Resource();
        depResource.setId(departmentId);
        when(resourceService.getResourceByRealIdAndType(realDepId, ResourceType.DEPARTMENT)).thenReturn(depResource);
        when(banDepartmentService.isDepartmentBanned(departmentId)).thenReturn(false);

        when(accessRuleService
                .getById(departmentId, IssuerType.DEPARTMENT, resourceId, commandId)).thenReturn(Optional.empty());

        Long realOrgId = 6L;
        Long organisationId = 7L;
        when(orgsWebClient.fetchUserOrganizations(userId)).thenReturn(realOrgId);

        Resource orgResource = new Resource();
        orgResource.setId(organisationId);
        when(resourceService.getResourceByRealIdAndType(realOrgId, ResourceType.ORGANIZATION)).thenReturn(orgResource);
        when(banOrganizationService.isOrganizationBanned(organisationId)).thenReturn(true);

        assertThat(accessCheckService.hasAccess(userId, resourceId, commandId)).isFalse();
    }

    @Test
    @SneakyThrows
    void whenUserOrganisationAccessIsFalseThenAccessFalse(){
        Long userId = 1L;
        IssuerType issuerType = IssuerType.USER;
        Long resourceId = 2L;
        Long commandId = 3L;

        when(banUserService.isUserBanned(userId)).thenReturn(false);
        when(accessRuleService
                .getById(userId, issuerType, resourceId, commandId)).thenReturn(Optional.empty());

        Long realDepId = 4L;
        Long departmentId = 5L;
        when(orgsWebClient.fetchUserDepartments(userId)).thenReturn(realDepId);

        Resource depResource = new Resource();
        depResource.setId(departmentId);
        when(resourceService.getResourceByRealIdAndType(realDepId, ResourceType.DEPARTMENT)).thenReturn(depResource);
        when(banDepartmentService.isDepartmentBanned(departmentId)).thenReturn(false);

        when(accessRuleService
                .getById(departmentId, IssuerType.DEPARTMENT, resourceId, commandId)).thenReturn(Optional.empty());

        Long realOrgId = 6L;
        Long organisationId = 7L;
        when(orgsWebClient.fetchUserOrganizations(userId)).thenReturn(realOrgId);

        Resource orgResource = new Resource();
        orgResource.setId(organisationId);
        when(resourceService.getResourceByRealIdAndType(realOrgId, ResourceType.ORGANIZATION)).thenReturn(orgResource);
        when(banOrganizationService.isOrganizationBanned(organisationId)).thenReturn(false);

        AccessRule orgAccessRule = new AccessRule(organisationId, issuerType, resourceId, commandId, false);
        when(accessRuleService
                .getById(organisationId, IssuerType.ORGANIZATION, resourceId, commandId)).thenReturn(Optional.of(orgAccessRule));

        assertThat(accessCheckService.hasAccess(userId, resourceId, commandId)).isFalse();
    }

    @Test
    @SneakyThrows
    void whenUserOrganisationAccessIsTrueThenAccessTrue(){
        Long userId = 1L;
        IssuerType issuerType = IssuerType.USER;
        Long resourceId = 2L;
        Long commandId = 3L;

        when(banUserService.isUserBanned(userId)).thenReturn(false);
        when(accessRuleService
                .getById(userId, issuerType, resourceId, commandId)).thenReturn(Optional.empty());

        Long realDepId = 4L;
        Long departmentId = 5L;
        when(orgsWebClient.fetchUserDepartments(userId)).thenReturn(realDepId);

        Resource depResource = new Resource();
        depResource.setId(departmentId);
        when(resourceService.getResourceByRealIdAndType(realDepId, ResourceType.DEPARTMENT)).thenReturn(depResource);
        when(banDepartmentService.isDepartmentBanned(departmentId)).thenReturn(false);

        when(accessRuleService
                .getById(departmentId, IssuerType.DEPARTMENT, resourceId, commandId)).thenReturn(Optional.empty());

        Long realOrgId = 6L;
        Long organisationId = 7L;
        when(orgsWebClient.fetchUserOrganizations(userId)).thenReturn(realOrgId);

        Resource orgResource = new Resource();
        orgResource.setId(organisationId);
        when(resourceService.getResourceByRealIdAndType(realOrgId, ResourceType.ORGANIZATION)).thenReturn(orgResource);
        when(banOrganizationService.isOrganizationBanned(organisationId)).thenReturn(false);

        AccessRule orgAccessRule = new AccessRule(organisationId, issuerType, resourceId, commandId, true);
        when(accessRuleService
                .getById(organisationId, IssuerType.ORGANIZATION, resourceId, commandId)).thenReturn(Optional.of(orgAccessRule));

        assertThat(accessCheckService.hasAccess(userId, resourceId, commandId)).isTrue();
    }

    @Test
    @SneakyThrows
    void whenUserOrganisationAccessRuleIsAbsentThenAccessFalse(){
        Long userId = 1L;
        IssuerType issuerType = IssuerType.USER;
        Long resourceId = 2L;
        Long commandId = 3L;

        when(banUserService.isUserBanned(userId)).thenReturn(false);
        when(accessRuleService
                .getById(userId, issuerType, resourceId, commandId)).thenReturn(Optional.empty());

        Long realDepId = 4L;
        Long departmentId = 5L;
        when(orgsWebClient.fetchUserDepartments(userId)).thenReturn(realDepId);

        Resource depResource = new Resource();
        depResource.setId(departmentId);
        when(resourceService.getResourceByRealIdAndType(realDepId, ResourceType.DEPARTMENT)).thenReturn(depResource);
        when(banDepartmentService.isDepartmentBanned(departmentId)).thenReturn(false);

        when(accessRuleService
                .getById(departmentId, IssuerType.DEPARTMENT, resourceId, commandId)).thenReturn(Optional.empty());

        Long realOrgId = 6L;
        Long organisationId = 7L;
        when(orgsWebClient.fetchUserOrganizations(userId)).thenReturn(realOrgId);

        Resource orgResource = new Resource();
        orgResource.setId(organisationId);
        when(resourceService.getResourceByRealIdAndType(realOrgId, ResourceType.ORGANIZATION)).thenReturn(orgResource);
        when(banOrganizationService.isOrganizationBanned(organisationId)).thenReturn(false);

        when(accessRuleService
                .getById(organisationId, IssuerType.ORGANIZATION, resourceId, commandId)).thenReturn(Optional.empty());

        assertThat(accessCheckService.hasAccess(userId, resourceId, commandId)).isFalse();
    }
}