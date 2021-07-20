package service;

import models.Transaction;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TransactionService {
    public static List<Transaction> getTopFive(List<Transaction> transactions) {
        return transactions
                .stream()
                .sorted(Comparator.comparingLong(Transaction::getAmount).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }

    public static int getNumberOfSuccessTransactions(List<Transaction> transactions) {
        return (int) transactions.stream().filter(t -> t.getStatus().equalsIgnoreCase("success") ||
                t.getStatus().equalsIgnoreCase("complete")).count();
    }

    public static long getMinAmount(List<Transaction> transactions) {
        return transactions.stream().mapToLong(Transaction::getAmount).min().orElse(-1);
    }

    public static long getMaxAmount(List<Transaction> transactions) {
        return transactions.stream().mapToLong(Transaction::getAmount).max().orElse(-1);
    }
}
