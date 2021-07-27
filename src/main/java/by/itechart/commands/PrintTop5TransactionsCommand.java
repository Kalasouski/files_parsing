package by.itechart.commands;

import by.itechart.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import by.itechart.service.TransactionService;

import java.util.List;

@Component
public class PrintTop5TransactionsCommand extends Command {
    private final TransactionService transactionService;

    @Autowired
    public PrintTop5TransactionsCommand(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


    @Override
    public void execute(List<Transaction> transactions) {
        transactionService.getTopFive(transactions).forEach(System.out::println);
    }

    @Override
    public String getDescription() {
        return "Print top-5 transactions";
    }
}
