package exceptions;

public class TransactionFileParsingException extends ApplicationException {
    public TransactionFileParsingException(String msg) {
        super(msg);
    }

    public TransactionFileParsingException(Exception e) {
        super(e);
    }
}
