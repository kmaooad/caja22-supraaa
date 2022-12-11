package edu.kmaooad;

import edu.kmaooad.commandDispatcher.CommandDispatcher;
import edu.kmaooad.commandHandlers.CommandHandler;
import edu.kmaooad.exceptions.AccessDeniedException;
import edu.kmaooad.processing.CommandCall;
import edu.kmaooad.services.interfaces.AccessCheckService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CommandDispatcherTest {

    @Autowired
    private CommandDispatcher commandDispatcher;

    @Autowired
    private Map<Long, CommandHandler> handlers;

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        public Map<Long, CommandHandler> handlers() {
            CommandHandler commandHandlerMock = mock(CommandHandler.class);
            return Map.of(0L, commandHandlerMock);
        }

        @Bean
        @Primary
        public AccessCheckService accessCheckService() {
            AccessCheckService service = mock(AccessCheckService.class);
            doReturn(true).when(service).hasAccess(any(), any(), any(), eq(0L));
            doReturn(false).when(service).hasAccess(any(), any(), any(), eq(1L));
            return service;
        }

    }

    @Test
    public void givenAllowedCommandCall_whenDispatch_shouldCallHandle() throws Exception {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[0]);
        commandDispatcher.dispatch(commandCall);
        verify(handlers.get(0L), times(1)).handle(commandCall);
    }

    @Test
    public void givenDeniedCommandCall_whenDispatch_shouldThrow() throws Exception {
        CommandCall commandCall = new CommandCall(1L, 123L, 456L, new String[0]);
        assertThrows(AccessDeniedException.class, () -> commandDispatcher.dispatch(commandCall));
        verify(handlers.get(0L), times(0)).handle(commandCall);
    }


}
