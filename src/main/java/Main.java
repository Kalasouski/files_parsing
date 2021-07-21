import commands.Command;
import controller.CommandInputController;
import exceptions.TransactionParserException;
import models.Transaction;
import org.apache.log4j.Logger;
import parsers.Parser;
import factories.ParserFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("Entering main method");
        String filePath;
        try {
            int index;
            index = args[0].indexOf("file=");
            if (index == -1)
                throw new IllegalArgumentException("No such substring");
            filePath = args[0].substring(args[0].indexOf("file=") + 5);
        } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
            log.error("Invalid filepath. Exception thrown: ", e);
            return;
        }
        Parser parser;
        try {
            parser = ParserFactory.resolveParser(filePath.substring(filePath.lastIndexOf('.') + 1));
        } catch (TransactionParserException e) {
            log.error("Exception thrown resolving parser: ", e);
            e.printStackTrace();
            return;
        }
        if (!new File(filePath).exists()) {
            log.error("No " + filePath + " file exists");
            System.err.println("The " + "'" + filePath + "'" + " is invalid path for a file");
            return;
        }
        log.info("File exists");
        System.out.println("The " + "'" + filePath + "'" + " is a file");
        List<Transaction> transactions;
        try {
            log.info("Entering getTransactionsList method in " + parser.getClass().getName());
            transactions = parser.parseFile(filePath);
        } catch (TransactionParserException e) {
            log.error("Exception thrown while parsing: ", e);
            e.printStackTrace();
            return;
        }
        System.out.println(
                """
                        File parsed successfully
                        Select operation:"""
        );
        Command command;
        try {
            command = new CommandInputController().getCommandById();
        } catch (TransactionParserException | IOException e) {
            e.printStackTrace();
            return;
        }
        command.execute(transactions);
    }
}
