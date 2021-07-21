package commands;

import models.Transaction;
import service.TransactionService;

import java.util.List;

public class Top5TransactionsPrinter implements Command {

    @Override
    public void execute(List<Transaction> transactions) {
        Command.printTransactions(TransactionService.getTopFive(transactions));
    }

    @Override
    public String getDescription() {
        return "Print top-5 transactions";
    }
}
