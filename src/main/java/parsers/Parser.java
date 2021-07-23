package parsers;

import exceptions.ApplicationException;
import exceptions.TransactionParserException;
import models.Transaction;

import java.util.List;

public interface Parser {
    List<Transaction> parseFile(String path) throws ApplicationException;

    String getSupportedExtension();
}
