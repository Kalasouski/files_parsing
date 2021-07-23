package exceptions.factory;

public class CommandResolvingException extends ApplicationFactoryException {
    public CommandResolvingException(Exception e) {
        super(e);
    }

    public CommandResolvingException(String msg) {
        super(msg);
    }
}
