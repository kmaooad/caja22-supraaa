package edu.kmaooad;

import edu.kmaooad.apiCommunication.TelegramWebClient;
import edu.kmaooad.config.BotPropertiesConfig;
import edu.kmaooad.exceptions.TelegramBadRequestException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

public class TelegramWebClientTest {

    private static TelegramWebClient webClient;
    private static final String TOKEN = "5744895470:AAGVTEtxpQ322706Z5Qo0YSvVJDAOVXLveo";
    private static final Long CHAT_ID = 569520498L;

    @BeforeAll
    public static void init() {
        BotPropertiesConfig config = spy(BotPropertiesConfig.class);
        doReturn(TOKEN).when(config).getToken();
        webClient = new TelegramWebClient(config);
    }

    @Test
    public void sendMessage_whenChatIdProvided_thenResponseIsOK() {
        boolean res = webClient.sendMessage("Hello from Test", CHAT_ID);
        Assertions.assertTrue(res);
    }

    @Test
    public void sendMessage_whenChatIdIsNotCorrect_thenBadRequest() {
        Assertions.assertThrows(TelegramBadRequestException.class, () -> {
            webClient.sendMessage("Hello from Test2", 1L);
        });
    }



}
