package commands;

import lombok.AllArgsConstructor;
import models.Transaction;

import java.util.List;

@AllArgsConstructor
public abstract class Command {
    public abstract void execute(List<Transaction> transactions);

    public abstract String getDescription();
}
