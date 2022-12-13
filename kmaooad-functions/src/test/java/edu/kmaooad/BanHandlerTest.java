package edu.kmaooad;

import edu.kmaooad.commandHandlers.BanHandler;
import edu.kmaooad.events.HandlerEvent;
import edu.kmaooad.events.listeners.HandlerEventListener;
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
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BanHandlerTest extends BaseTest {

    @Autowired
    private BanHandler banHandler;

    @Autowired
    private HandlerEventListener eventListener;

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        public BanUserService banUserService() {
            BanUserService service = mock(BanUserService.class);
            doReturn(true).when(service).isUserBanned(eq(1L));
            return service;
        }

        @Bean
        @Primary
        public BanDepartmentService banDepartmentService() {
            BanDepartmentService service = mock(BanDepartmentService.class);
            doReturn(true).when(service).isDepartmentBanned(eq(5L));
            return service;
        }

        @Bean
        @Primary
        public BanOrganizationService banOrganizationService() {
            BanOrganizationService service = mock(BanOrganizationService.class);
            doReturn(true).when(service).isOrganizationBanned(eq(0L));
            return service;
        }

    }

    @Test
    public void givenCorrectCommandCall_whenHandle_shouldCreateBanAndTriggerService() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"USER", "0"});
        Long issuerId = 0L;
        assertDoesNotThrow(() -> banHandler.handle(commandCall));
        verify(banUserService).isUserBanned(eq(issuerId));
        verify(banUserService).addBanForUser(eq(issuerId));
    }

    @Test
    public void givenCommandCall_whenBanAlreadyBannedUser_shouldThrowAndTriggerService() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"USER", "1"});
        assertThrows(Exception.class, () -> banHandler.handle(commandCall));
        verify(banUserService).isUserBanned(eq(1L));
    }

    @Test
    public void givenCommandCall_whenArgAbsentAndHandle_shouldThrow() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"USER"});
        assertThrows(Exception.class, () ->  banHandler.handle(commandCall));
    }

    @Test
    public void givenCommandCall_whenArgTypeIncorrectAbsentAndHandle_shouldThrow() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"INCORRECT_TYPE", "0"});
        assertThrows(Exception.class, () ->  banHandler.handle(commandCall));
    }

    @Test
    public void givenCommandCall_whenBanOrganization_shouldBandAndTriggerService() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"ORGANIZATION", "2"});
        assertDoesNotThrow(() -> banHandler.handle(commandCall));
        Long issuerId = 2L;
        verify(banOrganizationService).isOrganizationBanned(eq(issuerId));
        verify(banOrganizationService).addBanForOrganization(eq(issuerId));
    }

    @Test
    public void givenCommandCall_whenBanAlreadyBannedOrganization_shouldThrowAndTriggerService() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"ORGANIZATION", "0"});
        assertThrows(Exception.class, () -> banHandler.handle(commandCall));
        verify(banOrganizationService).isOrganizationBanned(eq(0L));
    }

    @Test
    public void givenCommandCall_whenBanDepartment_shouldBanAndTriggerService() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"DEPARTMENT", "2"});
        Long issuerId = 2L;
        assertDoesNotThrow(() -> banHandler.handle(commandCall));
        verify(banDepartmentService).isDepartmentBanned(eq(issuerId));
        verify(banDepartmentService).addBanForDepartment(eq(issuerId));
    }

    @Test
    public void givenCommandCall_whenBanAlreadyBannedDepartment_shouldThrowAndTriggerService() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"DEPARTMENT", "5"});
        assertThrows(Exception.class, () -> banHandler.handle(commandCall));
        verify(banDepartmentService).isDepartmentBanned(eq(5L));
    }


}
