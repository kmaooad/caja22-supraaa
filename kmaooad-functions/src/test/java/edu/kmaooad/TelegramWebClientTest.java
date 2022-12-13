package edu.kmaooad;

import edu.kmaooad.apiCommunication.TelegramWebClient;
import edu.kmaooad.config.BotPropertiesConfig;
import edu.kmaooad.events.HandlerEvent;
import edu.kmaooad.events.listeners.HandlerEventListener;
import edu.kmaooad.exceptions.TelegramBadRequestException;
import edu.kmaooad.utils.WebClientRequestLogger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;


import static org.mockito.Mockito.*;

public class TelegramWebClientTest extends BaseTest {
    private static final String TOKEN = "5744895470:AAGVTEtxpQ322706Z5Qo0YSvVJDAOVXLveo";
    private static final Long CHAT_ID = 569520498L;
    private static final String baseUrl = new StringBuilder("https://api.telegram.org/bot")
                    .append(TOKEN)
                    .append("/sendMessage?chat_id=")
                    .toString();

    @TestConfiguration
    static class TestConfig {
        @Bean
        @Primary
        public WebClientRequestLogger webClientLogger() {
            WebClientRequestLogger log = mock(WebClientRequestLogger.class);
            doNothing().when(log).logRequest(anyString());
            return log;
        }
    }

    @Autowired
    private WebClientRequestLogger logger;

    @Test
    public void sendMessage_whenChatIdProvided_thenResponseIsOK() {
        String text = "Hello from Test";
        boolean res = webClient.sendMessage(text, CHAT_ID);
        Assertions.assertTrue(res);
        String req = new StringBuilder(baseUrl)
                .append(CHAT_ID)
                .append("&text=")
                .append(text.replaceAll("\\s+", "%20"))
                .toString();
        verify(logger, atLeastOnce()).logRequest(req);
    }

    @Test
    public void sendMessage_whenChatIdIsNotCorrect_thenBadRequest() {
        String msg2 = "Hello from Test2";
        Long chatId = 1L;
        String req = new StringBuilder(baseUrl)
                .append(chatId)
                .append("&text=")
                .append(msg2.replaceAll("\\s+", "%20"))
                .toString();
        Assertions.assertThrows(TelegramBadRequestException.class, () -> {
            webClient.sendMessage(msg2, chatId);
        });
        verify(logger, atLeastOnce()).logRequest(req);
    }

}
