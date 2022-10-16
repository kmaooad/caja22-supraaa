package edu.kmaooad;

import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.HttpMethod;
import com.microsoft.azure.functions.HttpRequestMessage;
import com.microsoft.azure.functions.HttpResponseMessage;
import com.microsoft.azure.functions.HttpStatus;
import com.microsoft.azure.functions.annotation.AuthorizationLevel;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.HttpTrigger;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

/**
 * Azure Functions with HTTP Trigger.
 */
public class Function {
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
            JSONObject object = new JSONObject(bodyString);
            long messageId = object.getJSONObject("message").getLong("message_id");


            String uri = "mongodb+srv://lab2user:1hdV7n0PGngIKpqX@cluster0.ctfiz.mongodb.net/?retryWrites=true&w=majority";
            try (MongoClient mongoClient = MongoClients.create(uri)) {
                MongoDatabase database = mongoClient.getDatabase("supraaa-db");
                MongoCollection<Document> collection = database.getCollection("updates");
                Document doc = Document.parse(bodyString);
                collection.insertOne(doc);
            }


            return request.createResponseBuilder(HttpStatus.OK).body("message_id="+messageId).build();
        } catch (JSONException | EmptyRequestBodyException e) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Request body must not be blank and must have message object with message_id field").build();
        }
    }
}
