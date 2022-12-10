package edu.kmaooad;

import edu.kmaooad.commandHandlers.CreateAccessRuleHandler;
import edu.kmaooad.exceptions.IncorrectResourceParamsException;
import edu.kmaooad.models.Command;
import edu.kmaooad.models.Resource;
import edu.kmaooad.models.ResourceType;
import edu.kmaooad.processing.CommandCall;
import edu.kmaooad.services.interfaces.AccessRuleService;
import edu.kmaooad.services.interfaces.CommandService;
import edu.kmaooad.services.interfaces.ResourceService;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
public class CreateAccessRuleHandlerTest {

    @Autowired
    private CreateAccessRuleHandler createAccessRuleHandler;


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
            return mock(AccessRuleService.class);
        }

        @Bean
        @Primary
        public ApplicationEventPublisher applicationEventPublisher() {
            return mock(ApplicationEventPublisher.class);
        }

    }

    @Test
    public void givenCorrectCommandCall_whenHandle_shouldCreateAccessRule() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"111111", "USER", "222222", "USER", "createStudent", "true" });
        assertDoesNotThrow(() -> createAccessRuleHandler.handle(commandCall));
    }

    @Test
    public void givenCommandCall_whenArgAbsentAndHandle_shouldThrow() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"111111", "USER", "222222", "USER", "createStudent"});
        assertThrows(Exception.class, () -> createAccessRuleHandler.handle(commandCall));
    }

    @Test
    public void givenCommandCall_whenArgTypeIncorrectAbsentAndHandle_shouldThrow() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"111111", "RESOURCE", "222222", "USER", "createStudent", "true" });
        assertThrows(Exception.class, () -> createAccessRuleHandler.handle(commandCall));
    }

}
