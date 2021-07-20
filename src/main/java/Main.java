import exceptions.TransactionParserException;
import models.Transaction;
import org.apache.log4j.Logger;
import parsers.CsvParser;
import parsers.Parser;
import parsers.XmlParser;
import service.TransactionService;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
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
        if (filePath.endsWith(".xml")) {
            log.info("xml file format indicated");
            parser = new XmlParser();
        } else if (filePath.endsWith(".csv")) {
            log.info("csv file format indicated");
            parser = new CsvParser();
        } else {
            log.error("Could not detect file format");
            System.err.println("Invalid format");
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
            transactions = parser.getTransactionsList(fileToString(filePath));
        } catch (TransactionParserException | IOException e) {
            log.error("Exception thrown while parsing: ", e);
            e.printStackTrace();
            return;
        }
        System.out.println(
                """
                        File parsed successfully
                        Select operation:
                         [1] Print data to display
                         [2] Print top-5 transactions
                         [3] Print totals
                        """
        );
        //for testing
        int input;
        try {
            input = getCommand();
        } catch (IOException e) {
            log.error("Exception thrown while reading number: ", e);
            System.err.println("Something went wrong during reading command");
            e.printStackTrace();
            return;
        }

        switch (input) {
            case 1 -> printTransactions(transactions);
            case 2 -> printTransactions(TransactionService.getTopFive(transactions));
            case 3 -> printTotals(transactions);
        }
    }

    private static int getCommand() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                String s = br.readLine();
                int x;
                if (s.length() == 1 && Character.isDigit(s.charAt(0)) && (x = Integer.parseInt(s)) >= 1 &&
                        x <= 3)
                    return x;
                System.out.println("Bad input. Try again");
            }
        }
    }

    private static void printTransactions(List<Transaction> transactions) {
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            System.out.println("Transaction №" + (i + 1) + " data:");
            System.out.println("\tid: " + transaction.getId());
            System.out.println("\tuser_id: " + transaction.getUserId());
            System.out.println("\tdate: " + transaction.getDate());
            System.out.println("\tamount: " + transaction.getAmount());
            System.out.println("\tcurrency: " + transaction.getCurrency());
            System.out.println("\tresult: " + transaction.getStatus());
        }
    }


    private static void printTotals(List<Transaction> transactions) {
        System.out.println("Total number of transactions: " + transactions.size());
        long successful = TransactionService.getNumberOfSuccessTransactions(transactions);
        System.out.println("\twhere successful: " + successful);
        System.out.println("\t\t\tfailed: " + (transactions.size() - successful));

        System.out.println("Max transaction: " + TransactionService.getMaxAmount(transactions));
        System.out.println("Min transaction: " + TransactionService.getMinAmount(transactions));
    }

    private static String fileToString(String filePath) throws IOException {
        return Files.readString(Path.of(filePath));
    }
}
