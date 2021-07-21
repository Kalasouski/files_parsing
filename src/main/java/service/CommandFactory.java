package service;

import commands.Command;
import exceptions.TransactionParserException;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandFactory {
    private final Map<Integer, Command> commandMap = new HashMap<>();

    public CommandFactory() throws TransactionParserException {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .filterInputsBy(new FilterBuilder().includePackage("commands"))
                .setUrls(ClasspathHelper.forPackage("commands")));

        Set<Class<? extends Command>> commandClasses = reflections.getSubTypesOf(Command.class);
        if (commandClasses.isEmpty())
            throw new TransactionParserException("No commands found");
        int id = 1;
        for (var commandClass : commandClasses) {
            Command command;
            try {
                command = commandClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new TransactionParserException(e);
            }
            commandMap.put(id++, command);
        }
    }

    public Command resolveCommand(int id) throws TransactionParserException {
        Command command = commandMap.get(id);
        if (command == null)
            throw new TransactionParserException("Invalid id");
        return command;
    }

    public void printCommands() {
        int i = 1;
        for(var entry : commandMap.entrySet()) {
            System.out.println("["+(i++)+"] "+ entry.getValue().getDescription());
        }
    }
}
