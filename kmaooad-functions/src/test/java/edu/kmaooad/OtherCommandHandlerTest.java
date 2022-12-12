package edu.kmaooad;

import edu.kmaooad.commandHandlers.OtherCommandHandler;
import edu.kmaooad.exceptions.NotFoundException;
import edu.kmaooad.models.Command;
import edu.kmaooad.processing.CommandCall;
import edu.kmaooad.services.interfaces.CommandService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OtherCommandHandlerTest {

    @Autowired
    private OtherCommandHandler otherCommandHandler;

    @TestConfiguration
    static class TestConfig {

        private final MockWebServer server;

        TestConfig() throws IOException {
            server = new MockWebServer();
            server.start();

            MockResponse response = new MockResponse()
                    .addHeader("Content-Type", "application/text")
                    .setBody("Student successfully created!");
            server.enqueue(response);
        }

        @Bean
        @Primary
        public CommandService commandService() {
            CommandService service = mock(CommandService.class);
            doReturn(new Command(0L,"createStudent",this.server.url("") + "/endpoint")).when(service).getCommandById(0L);
            doThrow(new NotFoundException(Command.class.getSimpleName())).when(service).getCommandById(1L);
            return service;
        }

        @Bean
        @Primary
        public WebClient webClient() {
            String rootUrl = this.server.url("").toString();
            return WebClient.create(rootUrl);
        }

        @Bean
        @Primary
        public ApplicationEventPublisher applicationEventPublisher() {
            return mock(ApplicationEventPublisher.class);
        }

    }

    @Test
    public void givenCorrectCommandCall_whenHandle_shouldSendRequestToAnotherMicroservice() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L,
                new String[]{"createStudent", "Petrenko", "Petro"});
        assertDoesNotThrow(() -> otherCommandHandler.handle(commandCall));
    }

    @Test
    public void givenCommandCall_whenCommandByIdAbsent_shouldThrow() {
        CommandCall commandCall = new CommandCall(1L, 123L, 456L,
                new String[]{"createStudent", "Petrenko", "Petro"});
        assertThrows(Exception.class, () -> otherCommandHandler.handle(commandCall));
    }

}
