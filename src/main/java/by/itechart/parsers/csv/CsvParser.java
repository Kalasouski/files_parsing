package by.itechart.parsers.csv;


import by.itechart.exceptions.TransactionFileParsingException;
import by.itechart.models.Transaction;
import by.itechart.parsers.Parser;
import by.itechart.utils.FileUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("csv")
public class CsvParser implements Parser {
    @Override
    public List<Transaction> parseFile(String path) throws TransactionFileParsingException {
        List<String> fileLines = FileUtils.getFileStrings(path); // захардкодил
        fileLines = fileLines.subList(1, fileLines.size());
        return getTransactionList(fileLines);
    }

    private List<Transaction> getTransactionList(List<String> fileLines) throws TransactionFileParsingException {
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
            throw new TransactionFileParsingException(e);
        }

        return transactionList;
    }

    @Override
    public String getSupportedExtension() {
        return "csv";
    }
}
