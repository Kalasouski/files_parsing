package exceptions;

import exceptions.ApplicationException;

public class TransactionFileParsingException extends ApplicationException {
    public TransactionFileParsingException(String msg) {
        super(msg);
    }

    public TransactionFileParsingException(Exception e) {
        super(e);
    }
}
