package edu.kmaooad;

import edu.kmaooad.exceptions.IncorrectCommandParamsException;
import edu.kmaooad.exceptions.NotFoundException;
import edu.kmaooad.models.Command;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommandServiceTest extends BaseTest {

    @AfterEach
    public void afterEach() {
        commandRepository.deleteAll();
    }

    @Test
    public void givenNewCommand_whenCreate_shouldBeCreated() throws IncorrectCommandParamsException, NotFoundException {
        long id = 0L;
        String commandName = "name";
        String functionUrl = "functionUrl";
        Assertions.assertFalse(commandService.existsById(id));
        commandService.createCommand(id, commandName, functionUrl);
        Command command = commandService.getCommandById(0L);
        Assertions.assertNotNull(command);
    }

    @Test
    public void givenNewCommandWithNullId_whenCreate_shouldThrow() {
        Long id = null;
        String commandName = "name";
        String functionUrl = "functionUrl";
        Assertions.assertThrows(IncorrectCommandParamsException.class, () -> {commandService.createCommand(id, commandName, functionUrl);});
    }

    @Test
    public void givenNewCommandWithNullName_whenCreate_shouldThrow() {
        Long id = 0L;
        String commandName = null;
        String functionUrl = "functionUrl";
        Assertions.assertThrows(IncorrectCommandParamsException.class, () -> {commandService.createCommand(id, commandName, functionUrl);});
    }

    @Test
    public void givenExistingCommand_whenUpdate_shouldBeUpdated() throws Exception {
        long id = 0L;
        String commandName = "name";
        String functionUrl = "functionUrl";
        String newCommandName = "name1";
        String newFunctionUrl = "functionUrl1";
        Assertions.assertFalse(commandService.existsById(id));
        commandService.createCommand(id, commandName, functionUrl);
        Command command = commandService.getCommandById(0L);
        Assertions.assertNotNull(command);
        commandService.updateCommand(0L, newCommandName, newFunctionUrl);
        command = commandService.getCommandById(0L);
        Assertions.assertNotNull(command);
        Assertions.assertEquals(command.getName(), newCommandName);
        Assertions.assertEquals(command.getFunctionUrl(), newFunctionUrl);
    }

    @Test
    public void givenNewCommand_whenUpdate_shouldThrow() throws Exception {
        long id = 0L;
        long newId = 1L;
        String commandName = "name";
        String functionUrl = "functionUrl";
        String newCommandName = "name1";
        String newFunctionUrl = "functionUrl1";
        Assertions.assertThrows(NotFoundException.class, () -> {commandService.getCommandById(id);});
        commandService.createCommand(id, commandName, functionUrl);
        Command command = commandService.getCommandById(id);
        Assertions.assertNotNull(command);
        Assertions.assertThrows(NotFoundException.class, () -> {commandService.updateCommand(newId, newCommandName, newFunctionUrl);});
    }

    @Test
    public void givenExistingCommand_whenDelete_shouldBeDeleted() throws IncorrectCommandParamsException {
        long id = 0L;
        String commandName = "name";
        String functionUrl = "functionUrl";
        Assertions.assertFalse(commandService.existsById(id));
        commandService.createCommand(id, commandName, functionUrl);
        Command command = commandService.getCommandById(0L);
        Assertions.assertNotNull(command);
        commandService.deleteCommandById(id);
        Assertions.assertFalse(commandService.existsById(id));
    }

}
