package exceptions.factory;

import exceptions.ApplicationException;

public class ApplicationFactoryException extends ApplicationException {
    public ApplicationFactoryException(String msg) {
        super(msg);
    }

    public ApplicationFactoryException(Exception e) {
        super(e);
    }
}
