package factories.config;

import commands.Command;
import exceptions.ApplicationException;
import factories.config.reflections.ReflectionsManager;
import parsers.Parser;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class CommandFactoryConfig {
    ReflectionsManager reflectionsManager;

    public CommandFactoryConfig(String packageToScan) {
        reflectionsManager = new ReflectionsManager(packageToScan);
    }

    public Map<Integer, Command> getCommandImplementations() throws ApplicationException {
        Set<Class<? extends Command>> classes = reflectionsManager.getSubTypesOf(Command.class);
        int commandId = 1;
        Map<Integer, Command> commands = new HashMap<>();
        for (var impl : classes) {
            Command command;
            try {
                command = impl.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new ApplicationException(e);
            }
            commands.put(commandId++,command);
        }
        return commands;
    }

}
