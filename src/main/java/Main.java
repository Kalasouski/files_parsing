import exceptions.ApplicationException;
import models.Transaction;
import parsers.Parser;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws ApplicationException {
        Application application = new Application();
        Map<String, String> applicationArguments = application.getKeyValueArgs(args);
        String path = applicationArguments.get("file");
        String extension = application.getExtension(path);
        Parser parser = application.resolveParser(extension);

        List<Transaction> transactions = parser.parseFile(path);
        System.out.println("File parsed successfully");
        application.initCommands();

        while (true) {
            application.printCommands();
            int commandId = application.readCommandId();
            if (commandId == 0)
                break;
            application.executeCommand(commandId, transactions);
            System.out.println();
        }
        System.out.println("Exiting...");
    }
}
