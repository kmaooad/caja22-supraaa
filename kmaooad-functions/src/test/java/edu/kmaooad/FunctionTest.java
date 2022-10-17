package edu.kmaooad;

import com.microsoft.azure.functions.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.*;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


/**
 * Unit test for Function class.
 */
public class FunctionTest {

    private static final String BAD_RESPONSE = "Request body must not be blank and must have message object with message_id field";

    private HttpRequestMessage<Optional<String>> getReq(Optional<String> queryBody) {
        // Setup
        @SuppressWarnings("unchecked")
        final HttpRequestMessage<Optional<String>> req = mock(HttpRequestMessage.class);

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
        return context;
    }

    @Test
    public void testFunctionWithoutBody() {
        // Invoke
        final HttpResponseMessage ret = new Function().run(getReq(Optional.empty()), getContext());
        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, ret.getStatus());
        assertEquals(BAD_RESPONSE, ret.getBody());
    }

    @Test
    public void testFunctionWithWrongStringInBody(){
        // Invoke
        final HttpResponseMessage ret = new Function().run(getReq(Optional.of("Azure")), getContext());
        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, ret.getStatus());
        assertEquals(BAD_RESPONSE, ret.getBody());
    }

    @Test
    public void testFunctionWithMessageWithoutMessageIdInBody(){
        // Invoke
        final HttpResponseMessage ret = new Function().run(getReq(Optional.of("{\n" +
                "    \"message\": {}\n" +
                "}")), getContext());
        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, ret.getStatus());
        assertEquals(BAD_RESPONSE, ret.getBody());
    }

    @Test
    public void testFunctionWithMessageIdWithoutMessageInBody(){
        // Invoke
        final HttpResponseMessage ret = new Function().run(getReq(Optional.of("{\n" +
                "    \"message_id\": 12345\n" +
                "}")), getContext());
        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, ret.getStatus());
        assertEquals(BAD_RESPONSE, ret.getBody());
    }

    @Test
    public void testFunctionWithWrongTypeOfMessageIdInBody(){
        // Invoke
        final HttpResponseMessage ret = new Function().run(getReq(Optional.of("{\n" +
                "    \"message\": {\n" +
                "        \"message_id\": some_id\n" +
                "    }\n" +
                "}")), getContext());
        // Verify
        assertEquals(HttpStatus.BAD_REQUEST, ret.getStatus());
        assertEquals(BAD_RESPONSE, ret.getBody());
    }

    @Test
    public void testFunctionWithCorrectBody(){
        // Invoke
        final HttpResponseMessage ret = new Function().run(getReq(Optional.of("{\n" +
                "    \"message\": {\n" +
                "        \"message_id\": 12345\n" +
                "    }\n" +
                "}")), getContext());
        // Verify
        assertEquals(HttpStatus.OK, ret.getStatus());
        assertEquals("message_id=12345", ret.getBody());
    }

}
