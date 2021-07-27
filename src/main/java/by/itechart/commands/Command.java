package by.itechart.commands;

import lombok.AllArgsConstructor;
import by.itechart.models.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
public abstract class Command {
    public abstract void execute(List<Transaction> transactions);

    public abstract String getDescription();
}
