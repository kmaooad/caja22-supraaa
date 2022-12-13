package edu.kmaooad;

import edu.kmaooad.commandHandlers.DeleteAccessRuleHandler;
import edu.kmaooad.commandHandlers.UpdateAccessRuleHandler;
import edu.kmaooad.exceptions.IncorrectResourceParamsException;
import edu.kmaooad.exceptions.NotFoundException;
import edu.kmaooad.models.Command;
import edu.kmaooad.models.IssuerType;
import edu.kmaooad.models.Resource;
import edu.kmaooad.models.ResourceType;
import edu.kmaooad.processing.CommandCall;
import edu.kmaooad.services.interfaces.AccessRuleService;
import edu.kmaooad.services.interfaces.CommandService;
import edu.kmaooad.services.interfaces.ResourceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class DeleteAccessRuleHandlerTest extends BaseTest {

    @Autowired
    protected DeleteAccessRuleHandler deleteAccessRuleHandler;

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        public CommandService commandService() {
            CommandService service = mock(CommandService.class);
            doReturn(new Command(0L,"name","functionUrl")).when(service).getCommandByName(any());
            return service;
        }

        @Bean
        @Primary
        public ResourceService resourceService() {
            ResourceService service = mock(ResourceService.class);
            try {
                doReturn(new Resource(0L, 10L, ResourceType.USER)).when(service).getResourceByRealIdAndType(any(), any());
                return service;
            } catch (IncorrectResourceParamsException e) {
                return null;
            }
        }

        @Bean
        @Primary
        public AccessRuleService accessRuleService() {
            AccessRuleService service = mock(AccessRuleService.class);
            doReturn(true).when(service).existsById(eq(22L), eq(IssuerType.USER), any(), any());
            return service;
        }

    }

    @Test
    public void givenCorrectCommandCall_whenHandle_shouldThrowBecauseNoSuchUser() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"111111", "USER", "222222", "USER", "createStudent", "true" });
        assertThrows(Exception.class, () -> deleteAccessRuleHandler.handle(commandCall));
    }

    @Test
    public void givenCommandCall_whenArgAbsentAndHandle_shouldThrow() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"111111", "USER", "222222", "USER", "createStudent"});
        assertThrows(Exception.class, () -> deleteAccessRuleHandler.handle(commandCall));
    }

    @Test
    public void givenCommandCall_whenArgTypeIncorrectAbsentAndHandle_shouldThrow() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"111111", "RESOURCE", "222222", "USER", "createStudent", "true" });
        assertThrows(Exception.class, () -> deleteAccessRuleHandler.handle(commandCall));
    }

    @Test
    public void givenCommandCall_whenHandleCorrectCommandCall_shouldTriggerServiceAndNotThrow() {
        CommandCall commandCall = new CommandCall(0L, 123L, 143L, new String[]{"22", "USER", "222222", "USER", "createStudent", "true" });
        final Long issuerId = 22L;
        assertDoesNotThrow(() -> deleteAccessRuleHandler.handle(commandCall));
        verify(accessRuleService).existsById(eq(issuerId), eq(IssuerType.USER), any(), any());
        verify(accessRuleService).deleteById(eq(issuerId), eq(IssuerType.USER), any(), any());
    }

    @Test
    public void givenCommandCall_whenAccessRuleDoesNotExist_shouldTriggerServiceAndThrowNotFound() {
        CommandCall commandCall = new CommandCall(0L, 123L, 143L, new String[]{"30", "DEPARTMENT", "222222", "USER", "createStudent", "true" });
        final Long issuerId = 30L;
        assertThrows(NotFoundException.class, () -> deleteAccessRuleHandler.handle(commandCall));
        verify(accessRuleService).existsById(eq(issuerId), eq(IssuerType.DEPARTMENT), any(), any());
        verify(accessRuleService, times(0)).deleteById(eq(issuerId), eq(IssuerType.USER), any(), any());
    }

}
