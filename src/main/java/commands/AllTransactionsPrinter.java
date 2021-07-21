package commands;

import models.Transaction;

import java.util.List;

public class AllTransactionsPrinter implements Command {
    @Override
    public void execute(List<Transaction> transactions) {
        Command.printTransactions(transactions);
    }

    @Override
    public String getDescription() {
        return "Print data to display";
    }
}
