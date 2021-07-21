package service;

import exceptions.TransactionParserException;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import parsers.Parser;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

public class ParserFactory {
    public static Parser resolveParser(String extension) throws TransactionParserException {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .filterInputsBy(new FilterBuilder().includePackage("parsers"))
                .setUrls(ClasspathHelper.forPackage("parsers")));
        Set<Class<? extends Parser>> parserClasses = reflections.getSubTypesOf(Parser.class);
        for (var parserClass : parserClasses) {
            Parser parser;
            try {
                parser = parserClass.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new TransactionParserException(e);
            }
            if (parser.getSupportedExtension().equals(extension))
                return parser;
        }
        throw new TransactionParserException("Invalid extension, corresponding parser not found");
    }
}
