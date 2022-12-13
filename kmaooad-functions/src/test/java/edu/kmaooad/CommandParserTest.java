package edu.kmaooad;

import edu.kmaooad.models.BotUpdate;
import edu.kmaooad.models.Command;
import edu.kmaooad.models.Message;
import edu.kmaooad.processing.CommandCall;
import edu.kmaooad.services.interfaces.CommandService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.mockito.Mockito.*;

public class CommandParserTest extends BaseTest {

    @TestConfiguration
    static class TestConfig {

        @Bean
        @Primary
        public CommandService commandService() {
            CommandService service = mock(CommandService.class);
            doReturn(new Command(0L, "createStudent", "")).when(service).getCommandByName(eq("createStudent"));
            return service;
        }
    }

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
