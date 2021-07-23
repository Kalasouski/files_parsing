package parsers;

import exceptions.TransactionFileParsingException;
import models.Transaction;

import java.util.List;

public abstract class ParserTest {
    private final Parser parser;

    protected ParserTest(Parser parser) {
        this.parser = parser;
    }

    private String getFullResourcePath(String path) {
        return getClass().getClassLoader().getResource(path).getPath();
    }

    public List<Transaction> parseFile(String path) throws TransactionFileParsingException {
        return parser.parseFile(getFullResourcePath(path));
    }
}
