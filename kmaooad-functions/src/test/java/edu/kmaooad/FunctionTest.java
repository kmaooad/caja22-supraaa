package edu.kmaooad;

import com.microsoft.azure.functions.*;
import edu.kmaooad.webhook.TelegramWebhookHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.*;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


/**
 * Unit test for Function class.
 */
//@SpringBootTest(classes = Application.class)
public class FunctionTest {

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

    @Autowired
    private ApplicationContext context;

    @Test
    public void testFunctionWithoutBody() {
        // Invoke
        final HttpResponseMessage ret = new TelegramWebhookHandler().run(getReq(Optional.empty()), getContext());
        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, ret.getStatus());
        assertEquals(BAD_RESPONSE, ret.getBody());
    }

    @Test
    public void testFunctionWithWrongStringInBody(){
        // Invoke
        final HttpResponseMessage ret = new TelegramWebhookHandler().run(getReq(Optional.of("Azure")), getContext());
        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, ret.getStatus());
        assertEquals(BAD_RESPONSE, ret.getBody());
    }

    @Test
    public void testFunctionWithMessageWithoutMessageIdInBody(){
        // Invoke
        final HttpResponseMessage ret = new TelegramWebhookHandler().run(getReq(Optional.of("{\n" +
                "    \"message\": {}\n" +
                "}")), getContext());
        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, ret.getStatus());
        assertEquals(BAD_RESPONSE, ret.getBody());
    }

    @Test
    public void testFunctionWithMessageIdWithoutMessageInBody(){
        // Invoke
        final HttpResponseMessage ret = new TelegramWebhookHandler().run(getReq(Optional.of("{\n" +
                "    \"message_id\": 12345\n" +
                "}")), getContext());
        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, ret.getStatus());
        assertEquals(BAD_RESPONSE, ret.getBody());
    }

    @Test
    public void testFunctionWithWrongTypeOfMessageIdInBody(){
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

//    @Test
//    public void testFunctionWithCorrectBody() {
//        // Invoke
//        final HttpResponseMessage ret = new TelegramWebhookHandler().run(getReq(Optional.of(
//                "{\n" +
//                        "    \"update_id\":23433,\n" +
//                        "    \"message\":{\n" +
//                        "        \"message_id\":111,\n" +
//                        "        \"from\":{\n" +
//                        "            \"id\":569520498,\n" +
//                        "            \"is_bot\":false,\n" +
//                        "            \"first_name\":\"Gleb\",\n" +
//                        "            \"username\":\"Pelagus21\",\n" +
//                        "            \"language_code\":\"uk\"\n" +
//                        "        },\n" +
//                        "        \"chat\":{\n" +
//                        "            \"id\":569520498,\n" +
//                        "            \"first_name\":\"Gleb\",\n" +
//                        "            \"username\":\"Pelagus21\",\n" +
//                        "            \"type\":\"private\"\n" +
//                        "        },\n" +
//                        "        \"date\":1666633719,\n" +
//                        "        \"text\":\"test hello from postman!!!!\"\n" +
//                        "    }\n" +
//                        "}"
//        )), getContext());
//        // Verify
//        assertEquals(HttpStatus.OK, ret.getStatus());
//        assertEquals("message_id=111", ret.getBody());
//    }

//    @Test
//    public void testFunctionWithCorrectBody2() {
//
//        FunctionInvoker<BotUpdate, String> handler = new FunctionInvoker<>(
//                TelegramWebhook.class);
//        BotUpdate upd = new BotUpdate();
//        upd.setUpdateId((long)23433);
//        Message msg = new Message();
//        msg.setText("Test text hello");
//        msg.setDate((long)1666633719);
//        msg.setMessageId((long)111);
//        Message.From from = new Message.From();
//        from.setId((long)569520498);
//        from.setBot(false);
//        from.setUsername("Username1");
//        from.setFirstName("Gleb");
//        from.setLanguageCode("uk");
//        Message.Chat chat = new Message.Chat();
//        chat.setId((long)569520498);
//        chat.setType("private");
//        chat.setUsername("Username1");
//        chat.setFirstName("Gleb");
//        msg.setChat(chat);
//        msg.setFrom(from);
//        upd.setMessage(msg);
//        String result = handler.handleRequest(upd, new ExecutionContext() {
//            @Override
//            public Logger getLogger() {
//                return Logger.getLogger(FunctionTest.class.getName());
//            }
//
//            @Override
//            public String getInvocationId() {
//                return "id1";
//            }
//
//            @Override
//            public String getFunctionName() {
//                return "TelegramWebhook";
//            }
//        });
//        handler.close();
//        assertEquals(result, "message_id=111");
//    }

}