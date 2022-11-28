package edu.kmaooad.services.implementations;

import edu.kmaooad.exceptions.IncorrectCommandParamsException;
import edu.kmaooad.exceptions.NotFoundException;
import edu.kmaooad.models.Command;
import edu.kmaooad.repositories.CommandRepository;
import edu.kmaooad.services.interfaces.CommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommandServiceImpl implements CommandService {

    private final CommandRepository commandRepository;

    @Autowired
    public CommandServiceImpl(CommandRepository commandRepository) {
        this.commandRepository = commandRepository;
    }

    @Override
    public Command createCommand(Long id, String name) throws IncorrectCommandParamsException {
        return commandRepository.save(validateCommand(id, name));
    }

    @Override
    public Command getCommandById(Long id) throws NotFoundException{
        return commandRepository.findById(id).orElseThrow(() -> new NotFoundException(Command.class.getSimpleName()));
    }

    @Override
    public Command updateCommand(Long id, String name) throws Exception {
        Command command = validateCommand(id, name);
        if(!existsById(id)) {
            throw new NotFoundException(Command.class.getSimpleName());
        }
        return commandRepository.save(command);
    }

    @Override
    public Command deleteCommandById(Long id) {
        Command toDelete = commandRepository.findById(id).orElse(null);
        if(toDelete != null)
            commandRepository.deleteById(id);
        return toDelete;
    }

    @Override
    public boolean existsById(Long id) {
        return commandRepository.existsById(id);
    }

    private Command validateCommand(Long id, String name) throws IncorrectCommandParamsException {
        if(id == null)
            throw new IncorrectCommandParamsException("id","null");
        if(name == null)
            throw new IncorrectCommandParamsException("name","null");
        return new Command(id,name);
    }

}
