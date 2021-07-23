import exceptions.ApplicationException;
import models.Transaction;
import org.apache.log4j.Logger;
import parsers.Parser;

import java.util.List;
import java.util.Map;


public class Main {
    private static final Logger log = Logger.getLogger(Main.class);

    public static void main(String[] args) throws ApplicationException {
        Application application = new Application();
        Map<String,String> keyValueArgs = application.getKeyValueArgs(args);
        String path = keyValueArgs.get("file");
        String extension = application.getExtension(path);
        Parser parser = application.resolveParser(extension);

        List<Transaction> transactions = parser.parseFile(path);
        System.out.println("File parsed successfully");
        application.initCommands();


        while (true) {
            application.printCommands();
            int commandId = application.readCommandId();
            if(commandId == 0)
                break;
            application.executeCommand(commandId,transactions);
        }
        System.out.println("Exiting...");
    }
}
