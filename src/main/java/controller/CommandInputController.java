package controller;

import commands.Command;
import exceptions.TransactionParserException;
import factories.CommandFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CommandInputController {
    private final CommandFactory commandFactory;

    public CommandInputController() throws TransactionParserException {
        commandFactory = new CommandFactory();
    }

    public Command getCommandById() throws IOException {
        commandFactory.printCommands();
        int id;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                id = Integer.parseInt(br.readLine());
                try {
                    return commandFactory.resolveCommand(id);
                } catch (TransactionParserException e) {
                    System.out.println("Invalid id. Try again");
                }
            }
        }
    }
}
