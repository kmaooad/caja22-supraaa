package edu.kmaooad.processing;

import edu.kmaooad.models.BotUpdate;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CommandParser {

    //@Autowired
    //private CommandService commandService;

    //general processor (mediator) operates with (executes) CommandCall object (saves to stack, etc.)
    public CommandCall parse(BotUpdate botUpdate) {
        String text = botUpdate.getMessage().getText();
        Long userId = botUpdate.getMessage().getFrom().getId();
        Long chatId = botUpdate.getMessage().getChat().getId();
        String[] res = text.split("\\s+");
        //retrieve commandId by commandName from commandService
        //Long commandId = this.commandService.getIdByName(res[0]);
        return new CommandCall(1L, chatId, userId, Arrays.copyOfRange(res, 1, res.length));
    }

}
