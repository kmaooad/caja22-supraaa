package edu.kmaooad;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import edu.kmaooad.exceptions.IncorrectResourceParamsException;
import edu.kmaooad.models.BotUpdate;
import edu.kmaooad.models.Command;
import edu.kmaooad.repositories.BotUpdateRepository;
import edu.kmaooad.services.interfaces.AccessCheckService;
import edu.kmaooad.services.interfaces.CommandService;
import edu.kmaooad.webhook.TelegramWebhook;
import edu.kmaooad.webhook.TelegramWebhookHandler;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Optional;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/**
 * Unit test for Function class.
 */
public class FunctionTest extends BaseTest {

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        public BotUpdateRepository getBotUpdateRepository() {
            BotUpdateRepository repository = mock(BotUpdateRepository.class);
            doReturn(null).when(repository).save(any(BotUpdate.class));
            return repository;
        }

        @Bean
        @Primary
        public AccessCheckService accessCheckService() throws IncorrectResourceParamsException {
            AccessCheckService service = mock(AccessCheckService.class);
            doReturn(true).when(service).hasAccess(any(), any(), any(), any());
            return service;
        }

        @Bean
        @Primary
        public CommandService commandService() {
            CommandService service = mock(CommandService.class);
            doReturn(new Command(0L, "createStudent", "")).when(service).getCommandByName(eq("createStudent"));
            return service;
        }
    }

    private static final String BAD_RESPONSE = "Request body must not be blank and must have message object with message_id field";

    private HttpRequestMessage<Optional<String>> getReq(Optional<String> queryBody) {
        // Setup
        @SuppressWarnings("unchecked") final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);

        doReturn(queryBody).when(req).getBody();

        doAnswer(new Answer<HttpResponseMessage.Builder>() {
            @Override
            public HttpResponseMessage.Builder answer(InvocationOnMock invocation) {
                HttpStatus status = (HttpStatus) invocation.getArguments()[0];
                return new HttpResponseMessageMock.HttpResponseMessageBuilderMock().status(status);
            }
        }).when(req).createResponseBuilder(any(HttpStatus.class));

        return req;
    }

    private ExecutionContext getContext() {
        // Setup
        final ExecutionContext context = mock(ExecutionContext.class);
        doReturn(Logger.getGlobal()).when(context).getLogger();
        doReturn("TelegramWebhook").when(context).getFunctionName();
        return context;
    }

    @Test
    public void testFunctionWithoutBody() {
        // Invoke
        final HttpResponseMessage ret = new TelegramWebhookHandler().run(getReq(Optional.empty()), getContext());
        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, ret.getStatus());
        assertEquals(BAD_RESPONSE, ret.getBody());
    }

    @Test
    public void testFunctionWithWrongStringInBody() {
        // Invoke
        final HttpResponseMessage ret = new TelegramWebhookHandler().run(getReq(Optional.of("Azure")), getContext());
        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, ret.getStatus());
        assertEquals(BAD_RESPONSE, ret.getBody());
    }

    @Test
    public void testFunctionWithMessageWithoutMessageIdInBody() {
        // Invoke
        final HttpResponseMessage ret = new TelegramWebhookHandler().run(getReq(Optional.of("{\n" +
                "    \"message\": {}\n" +
                "}")), getContext());
        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, ret.getStatus());
        assertEquals(BAD_RESPONSE, ret.getBody());
    }

    @Test
    public void testFunctionWithMessageIdWithoutMessageInBody() {
        // Invoke
        final HttpResponseMessage ret = new TelegramWebhookHandler().run(getReq(Optional.of("{\n" +
                "    \"message_id\": 12345\n" +
                "}")), getContext());
        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, ret.getStatus());
        assertEquals(BAD_RESPONSE, ret.getBody());
    }

    @Test
    public void testFunctionWithWrongTypeOfMessageIdInBody() {
        // Invoke
        final HttpResponseMessage ret = new TelegramWebhookHandler().run(getReq(Optional.of("{\n" +
                "    \"message\": {\n" +
                "        \"message_id\": some_id\n" +
                "    }\n" +
                "}")), getContext());
        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, ret.getStatus());
        assertEquals(BAD_RESPONSE, ret.getBody());
    }

    private TelegramWebhookHandler getHandler() {
        TelegramWebhookHandler handler = spy(TelegramWebhookHandler.class);
        doAnswer(invocationOnMock -> applicationContext.getBean(TelegramWebhook.class).apply(invocationOnMock.getArgument(0)))
                .when(handler).handleRequest(any(BotUpdate.class), any(ExecutionContext.class));
        return handler;
    }

    @Test
    public void testFunctionWithCorrectBody() {
        // Invoke
        final HttpResponseMessage ret = getHandler().run(getReq(Optional.of(
                "{\n" +
                        "    \"update_id\":23433,\n" +
                        "    \"message\":{\n" +
                        "        \"message_id\":111,\n" +
                        "        \"from\":{\n" +
                        "            \"id\":569520498,\n" +
                        "            \"is_bot\":false,\n" +
                        "            \"first_name\":\"Gleb\",\n" +
                        "            \"username\":\"Pelagus21\",\n" +
                        "            \"language_code\":\"uk\"\n" +
                        "        },\n" +
                        "        \"chat\":{\n" +
                        "            \"id\":569520498,\n" +
                        "            \"first_name\":\"Gleb\",\n" +
                        "            \"username\":\"Pelagus21\",\n" +
                        "            \"type\":\"private\"\n" +
                        "        },\n" +
                        "        \"date\":1666633719,\n" +
                        "        \"text\":\"createStudent 1 USER\"\n" +
                        "    }\n" +
                        "}"
        )), getContext());
        // Verify
        assertEquals(HttpStatus.OK, ret.getStatus());
        //assertEquals("message_id=111", ret.getBody());
    }

}