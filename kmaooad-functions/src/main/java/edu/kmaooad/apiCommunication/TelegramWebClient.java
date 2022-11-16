package edu.kmaooad.apiCommunication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.kmaooad.config.BotPropertiesConfig;
import edu.kmaooad.exceptions.IncorrectTelegramResponseBodyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TelegramWebClient {

    private WebClient webClient;

    private BotPropertiesConfig config;

    private static final String TG_BASE_URL = "https://api.telegram.org/bot";

    @Autowired
    public TelegramWebClient(BotPropertiesConfig config) {
        this.config = config;
        StringBuilder sb = new StringBuilder(TG_BASE_URL);
        sb.append(this.config.getToken());
        webClient = WebClient.builder().baseUrl(sb.toString()).build();
    }

    public boolean sendMessage(String text, Long chatId) {
        try {
            String response = this.webClient.get()
                    .uri(uriBuilder ->
                            uriBuilder.path("/sendMessage")
                                    .queryParam("chat_id", chatId)
                                    .queryParam("text", text)
                                    .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            ObjectNode node = new ObjectMapper().readValue(response, ObjectNode.class);
            return node.get("ok").asBoolean();
        } catch (JsonProcessingException | NullPointerException e) {
            e.printStackTrace();
            throw new IncorrectTelegramResponseBodyException();
        }
    }

}
