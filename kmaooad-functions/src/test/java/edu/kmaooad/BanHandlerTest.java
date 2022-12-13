package edu.kmaooad;

import edu.kmaooad.commandHandlers.BanHandler;
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
import static org.mockito.Mockito.mock;

@SpringBootTest
public class BanHandlerTest {

    @Autowired
    private BanHandler banHandler;

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        public BanUserService banUserService() {
            return mock(BanUserService.class);
        }

        @Primary
        public BanDepartmentService banDepartmentService() {
            return mock(BanDepartmentService.class);
        }


        @Primary
        public BanOrganizationService banOrganizationService() {
            return mock(BanOrganizationService.class);
        }

        @Bean
        @Primary
        public ApplicationEventPublisher applicationEventPublisher() {
            return mock(ApplicationEventPublisher.class);
        }

    }

    @Test
    public void givenCorrectCommandCall_whenHandle_shouldCreateBan() {
        CommandCall commandCall = new CommandCall(0L, 123L, 456L, new String[]{"USER", "0"});
        assertDoesNotThrow(() -> banHandler.handle(commandCall));
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

}
