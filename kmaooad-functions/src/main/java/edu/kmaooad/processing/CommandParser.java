package edu.kmaooad.processing;

import edu.kmaooad.models.BotUpdate;
import edu.kmaooad.models.Command;
import edu.kmaooad.services.interfaces.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CommandParser {


    @Autowired
    private CommandService commandService;

    //general processor (mediator) operates with (executes) CommandCall object (saves to stack, etc.)
    public CommandCall parse(BotUpdate botUpdate) {
        String text = botUpdate.getMessage().getText();
        Long userId = botUpdate.getMessage().getFrom().getId();
        Long chatId = botUpdate.getMessage().getChat().getId();
        String[] res = text.split("\\s+");
        //retrieve commandId by commandName from commandService
        Command command = this.commandService.getCommandByName(res[0]);
        return new CommandCall(command.getId(), chatId, userId, Arrays.copyOfRange(res, 1, res.length));
    }

}
