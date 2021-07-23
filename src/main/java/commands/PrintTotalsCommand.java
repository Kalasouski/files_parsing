package commands;

import models.Transaction;
import service.TransactionService;

import java.util.List;

public class PrintTotalsCommand extends Command {
    private final TransactionService transactionService = TransactionService.getInstance(); //захардкодил

    @Override
    public void execute(List<Transaction> transactions) {
        System.out.println("Total number of transactions: " + transactions.size());
        long successful = transactionService.getNumberOfSuccessTransactions(transactions);
        System.out.println("\twhere successful: " + successful);
        System.out.println("\t\t\tfailed: " + (transactions.size() - successful));

        System.out.println("Max transaction: " + transactionService.getMaxAmount(transactions));
        System.out.println("Min transaction: " + transactionService.getMinAmount(transactions));
    }

    @Override
    public String getDescription() {
        return "Print statistics";
    }
}
