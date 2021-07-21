package exceptions;

public class TransactionParserException extends Exception {
    public TransactionParserException() {
    }

    public TransactionParserException(Throwable e) {
        super(e);
    }

    public TransactionParserException(String msg) {
        super(msg);
    }
}
