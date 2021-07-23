package commands;

import lombok.AllArgsConstructor;
import models.Transaction;

import java.util.List;

@AllArgsConstructor
public abstract class Command {
    private final int commandId;

    public abstract void execute(List<Transaction> transactions);

    public abstract String getDescription();
}
