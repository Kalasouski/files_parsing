package factories.config;

import commands.Command;
import exceptions.ApplicationException;
import factories.config.reflections.ReflectionsManager;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CommandFactoryConfig {
    ReflectionsManager reflectionsManager;

    public CommandFactoryConfig(String packageToScan) {
        reflectionsManager = new ReflectionsManager(packageToScan);
    }

    public List<Command> getCommandImplementations() throws ApplicationException {
        Set<Class<? extends Command>> classes = reflectionsManager.getSubTypesOf(Command.class);
        int commandId = 1;
        List<Command> commands = new ArrayList<>();
        for (var impl : classes) {
            Command command;
            try {
                command = impl.getConstructor(Integer.TYPE).newInstance(commandId);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new ApplicationException(e);
            }
            commandId++;
            commands.add(command);
        }
        return commands;
    }

}
