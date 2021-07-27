package by.itechart;

import by.itechart.commands.Command;

import by.itechart.exceptions.ApplicationException;
import by.itechart.exceptions.ExtensionResolvingException;
import by.itechart.exceptions.factory.CommandResolvingException;
import by.itechart.factories.CommandFactory;
import by.itechart.factories.ParserFactory;
import lombok.extern.slf4j.Slf4j;
import by.itechart.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import by.itechart.parsers.Parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service //is ignored
public class Application {
    private final Map<Integer, Command> commands = new HashMap<>();

    public final CommandFactory commandFactory;

    private final ParserFactory parserFactory;

    //@Autowired
    public Application(CommandFactory commandFactory, ParserFactory parserFactory) {
        this.commandFactory = commandFactory;
        this.parserFactory = parserFactory;
    }



    public Map<String, String> getKeyValueArgs(String[] args) throws ApplicationException {
        log.info("sdgh");
        Map<String, String> arguments = new HashMap<>();

        for (String arg : args) {
            String[] pair = arg.split("=");
            if (pair.length != 2) {
                log.error("Invalid argument: " + arg);
                throw new ApplicationException("Invalid args");
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
        return parserFactory.getParser(extension);
    }

    public void initCommands() {
        AtomicInteger count=new AtomicInteger(0);
        List<Command> commands = commandFactory.getCommands();
        for(int i = 0;i<commands.size();i++) {
            this.commands.put(i+1,commands.get(i));
        }
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
