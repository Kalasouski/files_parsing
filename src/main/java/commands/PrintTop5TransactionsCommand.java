package commands;

import models.Transaction;
import service.TransactionService;

import java.util.List;

public class PrintTop5TransactionsCommand extends Command {
    private final TransactionService transactionService = new TransactionService(); //захардкодил

    public PrintTop5TransactionsCommand(int commandId) {
        super(commandId);
    }

    @Override
    public void execute(List<Transaction> transactions) {
        transactionService.getTopFive(transactions).forEach(System.out::println);
    }

    @Override
    public String getDescription() {
        return  "Print top-5 transactions";
    }
}
