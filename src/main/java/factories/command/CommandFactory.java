package factories.command;

import commands.Command;
import exceptions.ApplicationException;
import factories.config.CommandFactoryConfig;
import lombok.Singleton;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class CommandFactory {
    private static CommandFactory instance;
    private final List<Command> commands;

    private CommandFactory() throws ApplicationException {
        CommandFactoryConfig config = new CommandFactoryConfig("commands"); //захардкодил
        commands = config.getCommandImplementations();
    }

    public List<Command> getCommands() {
        return new ArrayList<>(this.commands);
    }

    public static CommandFactory getInstance() throws ApplicationException {
        if (instance==null)
            instance = new CommandFactory();
        return instance;
    }
}
