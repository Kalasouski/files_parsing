package by.itechart.exceptions.factory;


import by.itechart.exceptions.ApplicationException;

public class ApplicationFactoryException extends ApplicationException {
    public ApplicationFactoryException(String msg) {
        super(msg);
    }

    public ApplicationFactoryException(Exception e) {
        super(e);
    }
}
