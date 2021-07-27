package by.itechart.parsers;

import by.itechart.exceptions.TransactionFileParsingException;

import by.itechart.models.Transaction;

import java.util.List;

public interface Parser {
    List<Transaction> parseFile(String path) throws TransactionFileParsingException, TransactionFileParsingException;

    String getSupportedExtension();
}
