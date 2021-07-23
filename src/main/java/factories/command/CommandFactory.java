package factories.command;

import commands.Command;
import exceptions.ApplicationException;
import exceptions.factory.ApplicationFactoryException;
import factories.config.CommandFactoryConfig;
import lombok.Singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Singleton
public class CommandFactory {
    private static CommandFactory instance;
    private final Map<Integer, Command> commands;

    private CommandFactory() throws ApplicationFactoryException {
        CommandFactoryConfig config = new CommandFactoryConfig("commands"); //захардкодил
        commands = config.getCommandImplementations();
    }

    public Map<Integer, Command> getCommands() {
        return new HashMap<>(this.commands);
    }

    public static CommandFactory getInstance() throws ApplicationFactoryException {
        if (instance==null)
            instance = new CommandFactory();
        return instance;
    }
}
