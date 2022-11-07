package edu.kmaooad;

import edu.kmaooad.services.interfaces.BanService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BanTest {

    @Autowired
    private BanService banService;

    @Test
    public void banForUserShouldBeAdded() {
        banService.addBanForUser(1L);
        assertTrue(banService.isUserBanned(1L));
    }

    @Test
    public void banForUserShouldBeDeleted() {
        banService.deleteBanForUser(1L);
        assertFalse(banService.isUserBanned(1L));
    }

    @Test
    public void banForDepartmentShouldBeAdded() {
        banService.addBanForDepartment(1L);
        assertTrue(banService.isDepartmentBanned(1L));
    }

    @Test
    public void banForDepartmentShouldBeDeleted() {
        banService.deleteBanForDepartment(1L);
        assertFalse(banService.isDepartmentBanned(1L));
    }

    @Test
    public void banForOrganizationShouldBeAdded() {
        banService.addBanForOrganization(1L);
        assertTrue(banService.isOrganizationBanned(1L));
    }

    @Test
    public void banForOrganizationShouldBeDeleted() {
        banService.deleteBanForOrganization(1L);
        assertFalse(banService.isOrganizationBanned(1L));
    }

}
