package edu.kmaooad.webhook;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import edu.kmaooad.exceptions.EmptyRequestBodyException;
import edu.kmaooad.exceptions.IncorrectRequestBodyException;
import edu.kmaooad.models.BotUpdate;
import org.springframework.cloud.function.adapter.azure.FunctionInvoker;

import java.util.Optional;

public class TelegramWebhookHandler extends FunctionInvoker<BotUpdate, String> {

    /**
     * This function listens at endpoint "/api/TelegramWebhook". To invoke it using "curl" command in bash:
     * curl -d "HTTP Body" {your host}/api/TelegramWebhook
     */
    @FunctionName("TelegramWebhook")
    public HttpResponseMessage run(
            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS)
            HttpRequestMessage<Optional<String>> request,
            final ExecutionContext context) {
        context.getLogger().info("Java HTTP trigger processed a request.");

        try {
            final String bodyString = request.getBody().orElseThrow(EmptyRequestBodyException::new);
            ObjectMapper objectMapper = new ObjectMapper();
            BotUpdate botUpdate = objectMapper.readValue(bodyString, BotUpdate.class);
            String funcRes = handleRequest(botUpdate, context);
            return request
                    .createResponseBuilder(HttpStatus.OK)
                    .body(funcRes)
                    .build();
        } catch (IncorrectRequestBodyException | JsonProcessingException | EmptyRequestBodyException e) {
            e.printStackTrace();
            return request
                    .createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Request body must not be blank and must have message object with message_id field")
                    .build();
        }
    }

}
