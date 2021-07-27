package by.itechart.commands;

import by.itechart.models.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrintAllTransactionsCommand extends Command {
    @Override
    public void execute(List<Transaction> transactions) {
        transactions.forEach(System.out::println);
    }

    @Override
    public String getDescription() {
        return "Print data to display";
    }
}
