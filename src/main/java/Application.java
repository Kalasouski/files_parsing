import commands.Command;
import exceptions.ApplicationException;
import exceptions.CommandLineArgsException;
import exceptions.ExtensionResolvingException;
import exceptions.factory.ApplicationFactoryException;
import exceptions.factory.CommandResolvingException;
import factories.command.CommandFactory;
import factories.parser.ParserFactory;
import models.Transaction;
import parsers.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {

    private Map<Integer, Command> commands;

    public Map<String, String> getKeyValueArgs(String[] args) throws CommandLineArgsException {
        Map<String, String> arguments = new HashMap<>();

        for (String arg : args) {
            String[] pair = arg.split("=");
            if (pair.length != 2) {
                throw new CommandLineArgsException("Invalid args");
            }
            arguments.put(pair[0], pair[1]);
        }
        return arguments;
    }

    public String getExtension(String path) throws ExtensionResolvingException {
        if (path == null || path.isEmpty()) {
            throw new ExtensionResolvingException("Invalid path");
        }
        int pointIndex = path.lastIndexOf('.');
        if (pointIndex == -1 || pointIndex == path.length() - 1) {
            throw new ExtensionResolvingException("Invalid path");
        }
        return path.substring(pointIndex + 1);
    }

    public Parser resolveParser(String extension) throws ApplicationException {
        ParserFactory factory = ParserFactory.getInstance();
        return factory.getParser(extension);
    }

    public void initCommands() throws ApplicationFactoryException {
        CommandFactory commandFactory = CommandFactory.getInstance();
        commands = commandFactory.getCommands();
    }

    public void printCommands() {
        System.out.println("The List of available commands: \n[0] : Exit");
        for (var entry : commands.entrySet()) {
            System.out.println("[" + entry.getKey() + "] : " + entry.getValue().getDescription());
        }
    }

    public int readCommandId() throws ApplicationException {
        System.out.println("Select operation: ");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return Integer.parseInt(reader.readLine());
        } catch (IOException | NumberFormatException e) {
            throw new CommandResolvingException(e);
        }
    }

    public void executeCommand(int commandId, List<Transaction> transactions) throws CommandResolvingException {
        Command command = commands.get(commandId);
        if (command == null) {
            throw new CommandResolvingException("Invalid id " + commandId);
        }
        command.execute(transactions);
    }

}
