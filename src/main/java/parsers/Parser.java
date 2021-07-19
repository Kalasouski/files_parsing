package parsers;

import exceptions.TransactionParserException;
import models.Transaction;

import java.util.List;

public interface Parser {
    List<Transaction> getTransactionsList(String filePath) throws TransactionParserException;
}
