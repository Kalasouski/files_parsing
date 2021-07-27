package by.itechart.exceptions.factory;


public class ParserResolvingException extends ApplicationFactoryException {
    public ParserResolvingException(String msg) {
        super(msg);
    }

    public ParserResolvingException(Exception e) {
        super(e);
    }
}
