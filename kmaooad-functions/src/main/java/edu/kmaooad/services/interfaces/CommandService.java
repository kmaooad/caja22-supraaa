package edu.kmaooad.services.interfaces;

import edu.kmaooad.exceptions.IncorrectCommandParamsException;
import edu.kmaooad.exceptions.NotFoundException;
import edu.kmaooad.models.Command;

public interface CommandService {

    Command createCommand(Long id, String name) throws IncorrectCommandParamsException;

    Command getCommandById(Long id) throws NotFoundException;

    Command updateCommand(Long id, String name) throws Exception;

    Command deleteCommandById(Long id);

    boolean existsById(Long id);

}
