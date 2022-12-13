package edu.kmaooad.apiCommunication;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import edu.kmaooad.config.BotPropertiesConfig;
import edu.kmaooad.exceptions.IncorrectTelegramResponseBodyException;
import edu.kmaooad.exceptions.TelegramBadRequestException;
import edu.kmaooad.utils.WebClientRequestLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class TelegramWebClient {

    private WebClient webClient;

    private final BotPropertiesConfig config;

    private final WebClientRequestLogger logger;

    private static final String TG_BASE_URL = "https://api.telegram.org/bot";

    @Autowired
    public TelegramWebClient(BotPropertiesConfig config, WebClientRequestLogger logger) {
        this.config = config;
        this.logger = logger;
        StringBuilder sb = new StringBuilder(TG_BASE_URL);
        sb.append(this.config.getToken());
        webClient = WebClient.builder().baseUrl(sb.toString()).filter(logFilter()).build();
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
                    .onStatus(HttpStatus.BAD_REQUEST::equals,
                            resp -> resp.bodyToMono(String.class).map(TelegramBadRequestException::new))
                    .bodyToMono(String.class)
                    .block();
            ObjectNode node = new ObjectMapper().readValue(response, ObjectNode.class);
            return node.get("ok").asBoolean();
        } catch (JsonProcessingException | NullPointerException e) {
            e.printStackTrace();
            throw new IncorrectTelegramResponseBodyException();
        }
    }

    private ExchangeFilterFunction logFilter() {
        return (clientRequest, next) -> {
            logger.logRequest(clientRequest.url().toString());
            return next.exchange(clientRequest);
        };
    }

}
