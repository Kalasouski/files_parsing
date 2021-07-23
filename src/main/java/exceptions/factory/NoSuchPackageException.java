package exceptions.factory;

import exceptions.ApplicationException;

public class NoSuchPackageException extends ApplicationFactoryException {
    public NoSuchPackageException(String msg) {
        super(msg);
    }
}
