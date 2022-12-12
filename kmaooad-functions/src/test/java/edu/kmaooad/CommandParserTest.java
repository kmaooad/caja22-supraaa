package edu.kmaooad;

import edu.kmaooad.models.BotUpdate;
import edu.kmaooad.models.Message;
import edu.kmaooad.processing.CommandCall;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class CommandParserTest extends BaseTest {

    @Test
    public void parseCommand_whenCorrectCommandInBotUpdate_thenReturnCommandCallObject() {
        BotUpdate upd = new BotUpdate();
        upd.setUpdateId(23433L);
        Message msg = new Message();
        Message.Chat chat = mock(Message.Chat.class);
        Message.From from = spy(Message.From.class);
        doReturn(569520498L).when(from).getId();
        msg.setFrom(from);
        msg.setChat(chat);
        msg.setText("createStudent -id 22223");
        upd.setMessage(msg);
        CommandCall call = parser.parse(upd);
        Assertions.assertEquals(call.getArgs().length, 2);
        Assertions.assertEquals(call.getUserId(), from.getId());
        Assertions.assertEquals(call.getArgs()[1], "22223");
    }

}
