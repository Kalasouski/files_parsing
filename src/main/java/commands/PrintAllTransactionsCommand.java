package commands;

import models.Transaction;

import java.util.List;

public class PrintAllTransactionsCommand extends Command {
    public PrintAllTransactionsCommand(int commandId) {
        super(commandId);
    }

    @Override
    public void execute(List<Transaction> transactions) {
        transactions.forEach(System.out::println);
    }

    @Override
    public String getDescription() {
        return "Print data to display";
    }
}
