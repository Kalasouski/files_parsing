package factories.config;

import exceptions.ApplicationException;
import factories.config.reflections.ReflectionsManager;
import parsers.Parser;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ParserFactoryConfig {
    ReflectionsManager reflectionsManager;

    public ParserFactoryConfig(String packageToScan) {
        reflectionsManager = new ReflectionsManager(packageToScan);
    }

    public Map<String, Parser> getParserImplementations() throws ApplicationException {
        Set<Class<? extends Parser>> classes = reflectionsManager.getSubTypesOf(Parser.class); //захардкодил
        Map<String, Parser> impls = new HashMap<>();
        for (var impl : classes) {
            Parser parser;
            try {
                parser = impl.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new ApplicationException(e);
            }
            impls.put(parser.getSupportedExtension(), parser);
        }
        return impls;
    }


}
