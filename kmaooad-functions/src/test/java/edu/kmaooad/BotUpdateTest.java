package edu.kmaooad;

import edu.kmaooad.models.BotUpdate;
import edu.kmaooad.models.Message;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class BotUpdateTest {

    @Test
    public void createBotUpdate_whenConstructorUsed_valuesShouldBeSaved() {
        long updateId = 1234L;
        Message message = mock(Message.class);
        BotUpdate botUpdate = new BotUpdate(updateId, message);
        assertEquals(updateId, botUpdate.getUpdateId());
        assertEquals(message, botUpdate.getMessage());
    }

}