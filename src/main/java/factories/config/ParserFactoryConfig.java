package factories.config;

import exceptions.ApplicationException;
import exceptions.factory.NoSuchPackageException;
import exceptions.factory.ParserResolvingException;
import factories.config.reflections.ReflectionsManager;
import parsers.Parser;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ParserFactoryConfig {
    ReflectionsManager reflectionsManager;

    public ParserFactoryConfig(String packageToScan) throws NoSuchPackageException {
        reflectionsManager = new ReflectionsManager(packageToScan);
    }

    public Map<String, Parser> getParserImplementations() throws ParserResolvingException {
        Set<Class<? extends Parser>> classes = reflectionsManager.getSubTypesOf(Parser.class); //захардкодил
        Map<String, Parser> impls = new HashMap<>();
        for (var impl : classes) {
            Parser parser;
            try {
                parser = impl.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new ParserResolvingException(e);
            }
            impls.put(parser.getSupportedExtension(), parser);
        }
        return impls;
    }


}
