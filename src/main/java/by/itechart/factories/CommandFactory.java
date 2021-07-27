package by.itechart.factories;

import by.itechart.commands.Command;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommandFactory {
    private final List<Command> commands;

    @Autowired
    public CommandFactory(List<Command> commands) {
        this.commands = commands;
    }

    public List<Command> getCommands() {
        return new ArrayList<>(this.commands);
    }
}
