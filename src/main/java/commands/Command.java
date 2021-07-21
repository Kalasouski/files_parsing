package commands;

import models.Transaction;

import java.util.List;

public interface Command {
    static void printTransactions(List<Transaction> transactions) {
        for (int i = 0; i < transactions.size(); i++) {
            Transaction transaction = transactions.get(i);
            System.out.println("Transaction №" + i + " data");
            System.out.println(transaction);
        }
    }

    void execute(List<Transaction> transactions);

    String getDescription();
}
