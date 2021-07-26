package factories;

import commands.Command;
import exceptions.factory.ApplicationFactoryException;
import exceptions.factory.CommandResolvingException;
import exceptions.factory.NoSuchPackageException;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CommandFactory {
    public static final String PACKAGE_TO_SCAN = "commands";
    private static CommandFactory instance;
    private final Map<Integer, Command> commands = new HashMap<>();

    private CommandFactory() throws NoSuchPackageException, CommandResolvingException {
        Reflections scanner;
        try {
            scanner = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forPackage(PACKAGE_TO_SCAN)));
        } catch (ReflectionsException e) {
            throw new NoSuchPackageException("No package " + PACKAGE_TO_SCAN + " found");
        }
        Set<Class<? extends Command>> classes = scanner.getSubTypesOf(Command.class);
        int commandId = 1;
        for (var impl : classes) {
            Command command;
            try {
                command = impl.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new CommandResolvingException(e);
            }
            commands.put(commandId++, command);
        }
    }

    public Map<Integer, Command> getCommands() {
        return new HashMap<>(this.commands);
    }

    public static CommandFactory getInstance() throws ApplicationFactoryException {
        if (instance == null)
            instance = new CommandFactory();
        return instance;
    }

}
