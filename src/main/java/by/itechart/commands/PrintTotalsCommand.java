package by.itechart.commands;

import by.itechart.models.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import by.itechart.service.TransactionService;

import java.util.List;

@Component
public class PrintTotalsCommand extends Command {
    private final TransactionService transactionService;// = TransactionService.getInstance(); //захардкодил

    @Autowired
    public PrintTotalsCommand(TransactionService transactionService) {
        this.transactionService = transactionService;
    }


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
