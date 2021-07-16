import models.Transaction;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;
import parsers.CsvParser;
import parsers.Parser;
import parsers.XmlParser;

import javax.xml.parsers.ParserConfigurationException;
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
            log.error("No such file exists");
            System.err.println("The " + "'" + filePath + "'" + " is invalid path for a file");
            return;
        }
        log.info("File exists");
        System.out.println("The " + "'" + filePath + "'" + " is a file");
        List<Transaction> transactions;
        try {
            log.info("Entering getTransactionsList method in " + parser.getClass().getName());
            transactions = parser.getTransactionsList(filePath);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("File parsed successfully");
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
        for (Transaction transaction : transactions)
            System.out.println(transaction.getId());

    }
}
