package edu.kmaooad;

import edu.kmaooad.models.Message;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class MessageTest {

    @Test
    public void createMessage_whenConstructorUsed_valuesShouldBeSaved() {
        long messageId = 1234L;
        long date = new Date().getTime();
        String text = "Some text for a message";
        Message.From from = mock(Message.From.class);
        Message.Chat chat = mock(Message.Chat.class);
        Message message = new Message(messageId, date, text, from, chat);
        assertEquals(messageId, message.getMessageId());
        assertEquals(date, message.getDate());
        assertEquals(text, message.getText());
        assertEquals(from, message.getFrom());
        assertEquals(chat, message.getChat());
    }

    @Test
    public void createFrom_whenConstructorUsed_valuesShouldBeSaved() {
        long id = 1234L;
        boolean isBot = true;
        String firstName = "FirstName";
        String username = "username";
        String langCode = "uk";
        Message.From from = new Message.From(id,isBot,firstName,username,langCode);
        assertEquals(id, from.getId());
        assertEquals(isBot, from.isBot());
        assertEquals(firstName, from.getFirstName());
        assertEquals(username, from.getUsername());
        assertEquals(langCode, from.getLanguageCode());
    }

    @Test
    public void createChat_whenConstructorUsed_valuesShouldBeSaved() {
        long id = 1234L;
        String firstName = "FirstName";
        String username = "username";
        String type = "someType";
        Message.Chat chat = new Message.Chat(id, firstName, username, type);
        assertEquals(id, chat.getId());
        assertEquals(firstName, chat.getFirstName());
        assertEquals(username, chat.getUsername());
        assertEquals(type, chat.getType());
    }

}
