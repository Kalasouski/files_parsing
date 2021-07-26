package factories;

import exceptions.factory.ApplicationFactoryException;
import exceptions.factory.NoSuchPackageException;
import exceptions.factory.ParserResolvingException;
import org.reflections.Reflections;
import org.reflections.ReflectionsException;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import parsers.Parser;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ParserFactory {
    public static final String PACKAGE_TO_SCAN = "parsers";
    private final Map<String, Parser> parserImpls = new HashMap<>();
    private static ParserFactory instance;

    private ParserFactory() throws NoSuchPackageException, ParserResolvingException {
        Reflections scanner;
        try {
            scanner = new Reflections(new ConfigurationBuilder()
                    .setUrls(ClasspathHelper.forPackage(PACKAGE_TO_SCAN)));
        } catch (ReflectionsException e) {
            throw new NoSuchPackageException("No package " + PACKAGE_TO_SCAN + " found");
        }
        Set<Class<? extends Parser>> classes = scanner.getSubTypesOf(Parser.class);
        for (var impl : classes) {
            Parser parser;
            try {
                parser = impl.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new ParserResolvingException(e);
            }
            parserImpls.put(parser.getSupportedExtension(), parser);
        }
    }

    public Parser getParser(String extension) throws ParserResolvingException {
        Parser parser = parserImpls.get(extension);
        if (parser == null)
            throw new ParserResolvingException("No such parser");
        return parser;
    }

    public static ParserFactory getInstance() throws ApplicationFactoryException {
        if (instance == null)
            instance = new ParserFactory();
        return instance;
    }
}
