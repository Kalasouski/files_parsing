package by.itechart.service;

import by.itechart.models.Transaction;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    public List<Transaction> getTopFive(List<Transaction> transactions) {
        return transactions
                .stream()
                .sorted(Comparator.comparingLong(Transaction::getAmount).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    public long getNumberOfSuccessTransactions(List<Transaction> transactions) {
        return transactions.stream().filter(t -> t.getStatus().equalsIgnoreCase("success") ||
                t.getStatus().equalsIgnoreCase("complete")).count();
    }

    public long getMinAmount(List<Transaction> transactions) {
        return transactions.stream().mapToLong(Transaction::getAmount).min().orElse(-1);
    }

    public long getMaxAmount(List<Transaction> transactions) {
        return transactions.stream().mapToLong(Transaction::getAmount).max().orElse(-1);
    }
}
