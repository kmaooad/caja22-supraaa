package edu.kmaooad;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BanTest extends BaseTest {

    @Test
    public void banForUserShouldBeAdded() {
        banUserService.addBanForUser(1L);
        assertTrue(banUserService.isUserBanned(1L));
    }

    @Test
    public void banForUserShouldBeDeleted() {
        banUserService.deleteBanForUser(1L);
        assertFalse(banUserService.isUserBanned(1L));
    }

    @Test
    public void banForDepartmentShouldBeAdded() {
        banDepartmentService.addBanForDepartment(1L);
        assertTrue(banDepartmentService.isDepartmentBanned(1L));
    }

    @Test
    public void banForDepartmentShouldBeDeleted() {
        banDepartmentService.deleteBanForDepartment(1L);
        assertFalse(banDepartmentService.isDepartmentBanned(1L));
    }

    @Test
    public void banForOrganizationShouldBeAdded() {
        banOrganizationService.addBanForOrganization(1L);
        assertTrue(banOrganizationService.isOrganizationBanned(1L));
    }

    @Test
    public void banForOrganizationShouldBeDeleted() {
        banOrganizationService.deleteBanForOrganization(1L);
        assertFalse(banOrganizationService.isOrganizationBanned(1L));
    }

}
