import commands.Command;
import exceptions.ApplicationException;
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

    public Map<String, String> getKeyValueArgs(String[] args) throws ApplicationException {
        Map<String, String> arguments = new HashMap<>();

        for (String arg : args) {
            String[] pair = arg.split("=");
            if (pair.length != 2) {
                throw new ApplicationException("Invalid args");
            }
            arguments.put(pair[0], pair[1]);
        }
        return arguments;
    }

    public String getExtension(String path) throws ApplicationException {
        if (path == null || path.isEmpty()) {
            throw new ApplicationException("Invalid path");
        }
        return path.substring(path.lastIndexOf('.') + 1);
    }

    public Parser resolveParser(String extension) throws ApplicationException {
        if (extension == null) {
            throw new ApplicationException("null extension");
        }
        ParserFactory factory = ParserFactory.getInstance();
        return factory.getParser(extension);
    }

    public void initCommands() throws ApplicationException {
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
            throw new ApplicationException(e);
        }
    }

    public void executeCommand(int commandId, List<Transaction> transactions) throws ApplicationException {
        Command command = commands.get(commandId);
        if (command == null) {
            throw new ApplicationException("Invalid id " + commandId);
        }
        command.execute(transactions);
    }

}
