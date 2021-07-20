package parsers;

import exceptions.TransactionParserException;
import models.Transaction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvParser implements Parser {
    @Override
    public List<Transaction> parseFile(String path) throws TransactionParserException {
        List<String> fileLines = null;
        try {
            fileLines = Files.readAllLines(Path.of(path));
        } catch (IOException e) {
            throw new TransactionParserException(e);
        }
        fileLines = fileLines.subList(1, fileLines.size());
        List<Transaction> transactionList = new ArrayList<>();
        try {
            for (String fileLine : fileLines) {
                String[] data = fileLine.split(",");
                String id = data[1];
                String userId = data[2];
                String date = data[0];
                long amount;
                amount = Long.parseLong(data[3]);
                String currency = data[4];
                String status = data[5];
                transactionList.add(new Transaction(id, userId, date, amount, currency, status));
            }
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new TransactionParserException(e);
        }

        return transactionList;
    }

    @Override
    public String getSupportedExtension() {
        return "csv";
    }
}
