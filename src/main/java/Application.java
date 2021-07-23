import exceptions.ApplicationException;
import factories.parser.ParserFactory;
import parsers.Parser;

import java.util.HashMap;
import java.util.Map;

public class Application {

    public Map<String, String> getKeyValueArgs(String[] args) throws ApplicationException {
        Map<String, String> arguments = new HashMap<>();

        for (String arg : args) {
            String[] pair = arg.split("=");
            if (pair.length != 2) {
                throw new ApplicationException("Invalid args");
            }
            arguments.put(pair[0], pair[1]);
        }
        return arguments;
    }

    public String getExtension(String path) throws ApplicationException {
        if (path == null || path.isEmpty()) {
            throw new ApplicationException("Invalid path");
        }
        return path.substring(path.lastIndexOf('.') + 1);
    }

    public Parser resolveParser(String extension) throws ApplicationException {
        if (extension == null) {
            throw new ApplicationException("null extension");
        }
        ParserFactory factory = ParserFactory.getInstance(); //захардкодил
        return factory.getParser(extension);
    }

}
