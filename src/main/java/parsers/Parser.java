package parsers;

import exceptions.TransactionFileParsingException;
import models.Transaction;

import java.util.List;

public interface Parser {
    List<Transaction> parseFile(String path) throws TransactionFileParsingException;

    String getSupportedExtension();
}
