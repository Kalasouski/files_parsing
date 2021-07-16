package parsers;

import models.Transaction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvParser implements Parser {
    public List<Transaction> getTransactionsList(String filePath) throws IOException {
        List<String> fileLines = Files.readAllLines(Paths.get(filePath));
        fileLines = fileLines.subList(1, fileLines.size());
        List<Transaction> transactionList = new ArrayList<>();
        for (String fileLine : fileLines) {
            String[] data = fileLine.split(",");
            String id = data[1];
            String userId = data[2];
            String date = data[0];
            long amount = Long.parseLong(data[3]);
            String currency = data[4];
            String status = data[5];
            transactionList.add(new Transaction(id, userId, date, amount, currency, status));
        }
        return transactionList;
    }
}
