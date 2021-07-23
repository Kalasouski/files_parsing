package factories.parser;

import exceptions.ApplicationException;
import exceptions.factory.ApplicationFactoryException;
import exceptions.factory.ParserResolvingException;
import factories.config.ParserFactoryConfig;
import lombok.Singleton;
import parsers.Parser;

import java.util.Map;

@Singleton
public class ParserFactory {
    private final Map<String,Parser> parserImpls;
    private static ParserFactory instance;

    private ParserFactory() throws ApplicationFactoryException {
        ParserFactoryConfig config = new ParserFactoryConfig("parsers"); //захардкодил
        parserImpls = config.getParserImplementations();
    }

    public Parser getParser(String extension) throws ParserResolvingException {
        Parser parser = parserImpls.get(extension);
        if(parser==null)
            throw new ParserResolvingException("No such parser");
        return parser;
    }

    public static ParserFactory getInstance() throws ApplicationException {
        if (instance==null)
            instance = new ParserFactory();
        return instance;
    }


}
