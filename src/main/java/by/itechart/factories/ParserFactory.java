package by.itechart.factories;

import by.itechart.exceptions.factory.ParserResolvingException;
import by.itechart.parsers.Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ParserFactory {

    private final Map<String,Parser> parsers;

    @Autowired
    public ParserFactory(Map<String,Parser> parsers) {
        this.parsers = parsers;
    }


    public Parser getParser(String extension) throws ParserResolvingException {
        Parser parser = parsers.get(extension);
        if (parser == null)
            throw new ParserResolvingException("No such parser");
        return parser;
    }
}
