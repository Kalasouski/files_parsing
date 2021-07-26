package exceptions.factory;

public class NoSuchPackageException extends ApplicationFactoryException {
    public NoSuchPackageException(String msg) {
        super(msg);
    }
}
