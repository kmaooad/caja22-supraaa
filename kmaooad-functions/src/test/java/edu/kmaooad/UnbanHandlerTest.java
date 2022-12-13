package edu.kmaooad;

import edu.kmaooad.commandHandlers.UnbanHandler;
import edu.kmaooad.processing.CommandCall;
import edu.kmaooad.services.interfaces.BanDepartmentService;
import edu.kmaooad.services.interfaces.BanOrganizationService;
import edu.kmaooad.services.interfaces.BanUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UnbanHandlerTest {

    @Autowired
    private UnbanHandler unbanHandler;

    @Autowired
    private BanUserService banUserService;

    @Autowired
    private BanDepartmentService banDepartmentService;

    @Autowired
    private BanOrganizationService banOrganizationService;

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        public BanUserService banUserService() {
            BanUserService service = mock(BanUserService.class);
            doReturn(true).when(service).isUserBanned(eq(100L));
            doReturn(false).when(service).isUserBanned(eq(200L));
            return service;
        }

        @Bean
        @Primary
        public BanDepartmentService banDepartmentService() {
            BanDepartmentService service = mock(BanDepartmentService.class);
            doReturn(true).when(service).isDepartmentBanned(eq(100L));
            doReturn(false).when(service).isDepartmentBanned(eq(200L));
            return service;
        }

        @Bean
        @Primary
        public BanOrganizationService banOrganizationService() {
            BanOrganizationService service = mock(BanOrganizationService.class);
            doReturn(true).when(service).isOrganizationBanned(eq(100L));
            doReturn(false).when(service).isOrganizationBanned(eq(200L));
            return service;
        }

        @Bean
        @Primary
        public ApplicationEventPublisher applicationEventPublisher() {
            return mock(ApplicationEventPublisher.class);
        }

    }

    @Test
    public void givenCorrectCommandCall_whenHandle_shouldThrowBecauseNoSuchUser() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"USER", "0"});
        assertThrows(Exception.class, () -> unbanHandler.handle(commandCall));
    }

    @Test
    public void givenCorrectCommandCall_whenDeleteBanUser_shouldDeleteSuccessfully() throws Exception {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"USER", "100"});
        unbanHandler.handle(commandCall);
        verify(banUserService, times(1)).deleteBanForUser(100L);
    }

    @Test
    public void givenCorrectCommandCall_whenDeleteBanUser_andUserNotBanned_shouldDeleteSuccessfully() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"USER", "200"});
        assertThrows(Exception.class, () -> unbanHandler.handle(commandCall));
    }

    @Test
    public void givenCorrectCommandCall_whenDeleteBanDept_shouldDeleteSuccessfully() throws Exception {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"DEPARTMENT", "100"});
        unbanHandler.handle(commandCall);
        verify(banDepartmentService, times(1)).deleteBanForDepartment(100L);
    }

    @Test
    public void givenCorrectCommandCall_whenDeleteBanUser_andDeptNotBanned_shouldDeleteSuccessfully() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"DEPARTMENT", "200"});
        assertThrows(Exception.class, () -> unbanHandler.handle(commandCall));
    }

    @Test
    public void givenCorrectCommandCall_whenDeleteBanOrg_shouldDeleteSuccessfully() throws Exception {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"ORGANIZATION", "100"});
        unbanHandler.handle(commandCall);
        verify(banOrganizationService, times(1)).deleteBanForOrganization(100L);
    }

    @Test
    public void givenCorrectCommandCall_whenDeleteBanUser_andOrgNotBanned_shouldDeleteSuccessfully() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"ORGANIZATION", "200"});
        assertThrows(Exception.class, () -> unbanHandler.handle(commandCall));
    }

    @Test
    public void givenCommandCall_whenArgAbsentAndHandle_shouldThrow() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"USER"});
        assertThrows(Exception.class, () ->  unbanHandler.handle(commandCall));
    }

    @Test
    public void givenCommandCall_whenArgTypeIncorrectAbsentAndHandle_shouldThrow() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"INCORRECT_TYPE", "0"});
        assertThrows(Exception.class, () ->  unbanHandler.handle(commandCall));
    }

}
