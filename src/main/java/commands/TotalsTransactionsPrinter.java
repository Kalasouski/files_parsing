package commands;

import models.Transaction;
import service.TransactionService;

import java.util.List;

public class TotalsTransactionsPrinter implements Command {
    @Override
    public void execute(List<Transaction> transactions) {
        System.out.println("Total number of transactions: " + transactions.size());
        long successful = TransactionService.getNumberOfSuccessTransactions(transactions);
        System.out.println("\twhere successful: " + successful);
        System.out.println("\t\t\tfailed: " + (transactions.size() - successful));

        System.out.println("Max transaction: " + TransactionService.getMaxAmount(transactions));
        System.out.println("Min transaction: " + TransactionService.getMinAmount(transactions));
    }

    @Override
    public String getDescription() {
        return "Print totals";
    }
}
